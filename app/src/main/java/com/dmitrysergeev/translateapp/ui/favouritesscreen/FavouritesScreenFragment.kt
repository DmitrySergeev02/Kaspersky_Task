package com.dmitrysergeev.translateapp.ui.favouritesscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.databinding.FragmentFavouritesScreenBinding
import com.dmitrysergeev.translateapp.ui.base.BaseFragment
import com.dmitrysergeev.translateapp.ui.favouritesscreen.recyclerview.FavouritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesScreenFragment: BaseFragment() {

    private var _binding: FragmentFavouritesScreenBinding? = null
    private val binding: FragmentFavouritesScreenBinding
        get() = checkNotNull(_binding)

    private val viewModel: FavouritesScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFavouritesScreenBinding.inflate(inflater, root.findViewById(R.id.content_container), true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseBinding.appBar.title = getString(R.string.favourites_page_title)

        baseBinding.appBar.setNavigationOnClickListener {
            baseBinding.drawerLayout.open()
        }

        baseBinding.navigationView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.main_page_item -> {
                    baseBinding.drawerLayout.close()
                    findNavController().popBackStack()
                }
                R.id.favourite_page_item -> { }
            }
            true
        }

        binding.favouriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FavouritesAdapter { item ->
            viewModel.deleteFromFavourites(item)
        }
        binding.favouriteRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.favouriteItems.collect{ favouriteItems->
                    adapter.favouritesItems = favouriteItems
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{ state->
                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.favouriteRecyclerView.visibility = if (state.isLoading) View.GONE else View.VISIBLE
                    if (state.snackbarTextId!=-1){
                        showSnackBarWithText(getString(state.snackbarTextId))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        baseBinding.navigationView.setCheckedItem(R.id.favourite_page_item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FavouritesScreenTag"
    }
}