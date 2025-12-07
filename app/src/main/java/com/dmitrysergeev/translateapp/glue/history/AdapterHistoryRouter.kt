package com.dmitrysergeev.translateapp.glue.history

import androidx.navigation.NavController
import com.dmitrysergeev.history.HistoryRouter
import com.dmitrysergeev.history.presentation.HistoryScreenFragmentDirections
import javax.inject.Inject

class AdapterHistoryRouter @Inject constructor(
    private val navController: NavController
): HistoryRouter {
    override fun navigateToTranslation() {
        navController.navigate(
            HistoryScreenFragmentDirections.toTranslation()
        )
    }
}