package com.dmitrysergeev.translateapp.ui.favouritesscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.databinding.FragmentBaseBinding
import com.dmitrysergeev.translateapp.databinding.FragmentFavouritesScreenBinding

class FavouritesScreenFragment: Fragment() {

    private var _baseBinding: FragmentBaseBinding? = null
    private val baseBinding: FragmentBaseBinding
        get() = checkNotNull(_baseBinding)

    private var _binding: FragmentFavouritesScreenBinding? = null
    private val binding: FragmentFavouritesScreenBinding
        get() = checkNotNull(_binding)

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
        baseBinding.navigationView.setCheckedItem(R.id.favourite_page_item)
        baseBinding.navigationView.setNavigationItemSelectedListener { menuItem->
            menuItem.isChecked = true
            baseBinding.drawerLayout.close()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _baseBinding = null
        _binding = null
    }

}