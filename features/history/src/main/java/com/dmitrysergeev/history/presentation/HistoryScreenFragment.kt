package com.dmitrysergeev.history.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.core.base.BaseFragment
import com.dmitrysergeev.history.HistoryRouter
import com.dmitrysergeev.history.R
import com.dmitrysergeev.history.databinding.FragmentHistoryScreenBinding
import com.dmitrysergeev.history.presentation.recyclerview.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.dmitrysergeev.core.R as R_base

@AndroidEntryPoint
class HistoryScreenFragment @Inject constructor(): BaseFragment() {

    private var _binding: FragmentHistoryScreenBinding? = null
    private val binding: FragmentHistoryScreenBinding
        get() = checkNotNull(_binding)

    private val viewModel: HistoryScreenViewModel by viewModels()

    @Inject
    lateinit var router: HistoryRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHistoryScreenBinding.inflate(inflater, root.findViewById(R_base.id.content_container), true)
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
                R_base.id.main_page_item -> {
                    baseBinding.drawerLayout.close()
                    router.navigateToTranslation()
                }
                R_base.id.history_page_item -> { }
            }
            true
        }

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryAdapter()
        binding.historyRecyclerView.adapter = adapter

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.favouritesItems[viewHolder.absoluteAdapterPosition]
                viewModel.deleteFromFavourites(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.historyRecyclerView)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.historyItems.collect{ favouriteItems->
                    adapter.favouritesItems = favouriteItems
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{ state->
                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.historyRecyclerView.visibility = if (state.isLoading) View.GONE else View.VISIBLE
                    if (state.snackbarTextId!=-1){
                        showSnackBarWithText(getString(state.snackbarTextId))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        baseBinding.navigationView.setCheckedItem(com.dmitrysergeev.core.R.id.history_page_item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FavouritesScreenTag"
    }
}