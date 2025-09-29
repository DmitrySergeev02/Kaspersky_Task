package com.dmitrysergeev.translateapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitrysergeev.translateapp.databinding.FragmentBaseBinding
import com.google.android.material.snackbar.Snackbar

open class BaseFragment: Fragment() {

    private var _baseBinding: FragmentBaseBinding? = null
    protected val baseBinding: FragmentBaseBinding
        get() = checkNotNull(_baseBinding)

    protected fun showSnackBarWithText(text: String){
        Snackbar.make(
            baseBinding.drawerLayout,
            text,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _baseBinding = FragmentBaseBinding.inflate(inflater, container, false)
        return baseBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _baseBinding = null
    }

}