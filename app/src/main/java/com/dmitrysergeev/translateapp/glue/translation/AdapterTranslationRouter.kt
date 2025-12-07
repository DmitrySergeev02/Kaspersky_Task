package com.dmitrysergeev.translateapp.glue.translation

import androidx.navigation.NavController
import com.dmitrysergeev.translation.TranslationRouter
import com.dmitrysergeev.translation.presentation.TranslationScreenFragmentDirections
import javax.inject.Inject

class AdapterTranslationRouter @Inject constructor(
    private val navController: NavController
): TranslationRouter {
    override fun navigateToHistory() {
        navController.navigate(
            TranslationScreenFragmentDirections.toHistory()
        )
    }
}