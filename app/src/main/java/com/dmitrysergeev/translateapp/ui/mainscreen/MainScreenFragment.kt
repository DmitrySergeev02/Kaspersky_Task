package com.dmitrysergeev.translateapp.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.databinding.FragmentMainScreenBinding
import com.dmitrysergeev.translateapp.ui.base.BaseFragment
import com.dmitrysergeev.translateapp.ui.mainscreen.recyclerview.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment: BaseFragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = checkNotNull(_binding)

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMainScreenBinding.inflate(inflater, root.findViewById(R.id.content_container), true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseBinding.appBar.title = getString(R.string.main_page_title)

        binding.textInputLayout.setEndIconOnClickListener {
            viewModel.translateText(binding.queryInput.text.toString())
            hideKeyboard(binding.queryInput)
        }

        baseBinding.appBar.setNavigationOnClickListener {
            baseBinding.drawerLayout.open()
        }

        baseBinding.navigationView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.main_page_item -> {  }
                R.id.favourite_page_item -> {
                    baseBinding.drawerLayout.close()
                    findNavController().navigate(
                        R.id.to_favourites_screen
                    )
                }
            }
            true
        }

        binding.queryInput.setOnEditorActionListener { textView, i, _ ->
            when(i){
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.translateText(binding.queryInput.text.toString())
                    hideKeyboard(binding.queryInput)
                    true
                }
                else -> false
            }
        }

        binding.queryInput.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.updateCurrentInputText(p0.toString())
            }

        })

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryAdapter(
            onDelete = { historyItem ->
                viewModel.deleteItemFromHistory(historyItem)
            }
        )
        binding.historyRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.historyItemsState.collect{ historyItems->
                    adapter.historyItems = historyItems
                }
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
                    binding.favouriteButton.setImageResource(if (state.isFavourite) R.drawable.like_icon else R.drawable.empty_like_icon)
                    binding.favouriteButton.setOnClickListener {
                        viewModel.changeFavouriteState(!state.isFavourite)
                    }
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
        outState.putString(QUERY_LAST_INPUT, viewModel.currentInputText.value)
    }

    override fun onResume() {
        super.onResume()
        baseBinding.navigationView.setCheckedItem(R.id.main_page_item)
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