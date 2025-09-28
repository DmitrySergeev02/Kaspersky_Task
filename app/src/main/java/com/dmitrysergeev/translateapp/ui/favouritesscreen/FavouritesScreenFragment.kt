package com.dmitrysergeev.translateapp.ui.favouritesscreen

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.databinding.FragmentBaseBinding
import com.dmitrysergeev.translateapp.databinding.FragmentFavouritesScreenBinding
import com.dmitrysergeev.translateapp.ui.favouritesscreen.recyclerview.FavouritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesScreenFragment: Fragment() {

    private var _baseBinding: FragmentBaseBinding? = null
    private val baseBinding: FragmentBaseBinding
        get() = checkNotNull(_baseBinding)

    private var _binding: FragmentFavouritesScreenBinding? = null
    private val binding: FragmentFavouritesScreenBinding
        get() = checkNotNull(_binding)

    private val viewModel: FavouritesScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _baseBinding = FragmentBaseBinding.inflate(inflater, container, false)
        _binding = FragmentFavouritesScreenBinding.inflate(inflater, baseBinding.contentContainer, true)
        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseBinding.appBar.title = "Избранное"

        baseBinding.appBar.setNavigationOnClickListener {
            baseBinding.drawerLayout.open()
        }

        baseBinding.navigationView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.main_page_item -> {
                    baseBinding.drawerLayout.close()
                    findNavController().navigate(
                        R.id.to_main_screen
                    )
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
    }

    override fun onResume() {
        super.onResume()
        baseBinding.navigationView.setCheckedItem(R.id.favourite_page_item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _baseBinding = null
        _binding = null
    }

    companion object {
        const val TAG = "FavouritesScreenTag"
    }
}