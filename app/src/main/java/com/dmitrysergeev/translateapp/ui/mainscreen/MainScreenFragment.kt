package com.dmitrysergeev.translateapp.ui.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.databinding.FragmentMainScreenBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainScreenFragment: Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = checkNotNull(_binding)

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showSnackBarWithText(text: String){
        Snackbar.make(
            binding.mainScreenLayout,
            text,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            viewModel.translateText(binding.queryInput.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.mainScreenUiState.collect{ state->
                    binding.wordTranslation.text = state.translateResult

                    if (state.snackbarText.isNotBlank()){
                        showSnackBarWithText(state.snackbarText)
                    }

                    binding.favouriteButton.visibility = if (state.translateResult.isBlank()) View.GONE else View.VISIBLE
                    binding.favouriteButton.setImageResource(if (state.isFavourite) R.drawable.like_icon else R.drawable.empty_like_icon)
                    binding.favouriteButton.setOnClickListener {
                        viewModel.changeFavouriteState(!state.isFavourite)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}