package com.dmitrysergeev.translation.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dmitrysergeev.core.base.BaseFragment
import com.dmitrysergeev.translation.R
import com.dmitrysergeev.translation.TranslationRouter
import com.dmitrysergeev.core.R as R_base
import com.dmitrysergeev.translation.databinding.FragmentTranslationScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TranslationScreenFragment @Inject constructor(): BaseFragment() {

    private var _binding: FragmentTranslationScreenBinding? = null
    private val binding: FragmentTranslationScreenBinding
        get() = checkNotNull(_binding)

    private val viewModel: TranslationScreenViewModel by viewModels()

    @Inject
    lateinit var router: TranslationRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentTranslationScreenBinding.inflate(inflater, root.findViewById(R_base.id.content_container), true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseBinding.appBar.title = getString(R.string.main_page_title)

        binding.textInputLayout.setEndIconOnClickListener {
            viewModel.translateText()
            hideKeyboard(binding.queryInput)
        }

        baseBinding.appBar.setNavigationOnClickListener {
            baseBinding.drawerLayout.open()
        }

        baseBinding.navigationView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R_base.id.main_page_item -> {  }
                R_base.id.history_page_item -> {
                    baseBinding.drawerLayout.close()
                    router.navigateToHistory()
                }
            }
            true
        }

        binding.queryInput.addTextChangedListener(afterTextChanged = { text: Editable? ->
            viewModel.updateInput(text.toString())
        })

        binding.queryInput.setOnEditorActionListener { _, i, _ ->
            when(i){
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.translateText()
                    hideKeyboard(binding.queryInput)
                    true
                }
                else -> false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){

                viewModel.mainScreenUiState.collect{ state->
                    binding.wordTranslation.text = state.translateResult

                    if (state.snackbarTextId!=-1){
                        showSnackBarWithText(getString(state.snackbarTextId))
                    }

                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.currentTranslationItem.visibility = if (state.translateResult.isBlank() || state.isLoading) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState?.containsKey(QUERY_LAST_INPUT) == true){
            binding.queryInput.setText(savedInstanceState.getString(QUERY_LAST_INPUT))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY_LAST_INPUT, viewModel.currentInput.value)
    }

    override fun onResume() {
        super.onResume()
        baseBinding.navigationView.setCheckedItem(R_base.id.main_page_item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MainScreenTag"
        const val QUERY_LAST_INPUT = "query_last_input"
    }
}