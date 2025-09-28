package com.dmitrysergeev.translateapp.ui.mainscreen

data class MainScreenUiState(
    val translateResult: String = "",
    val snackbarTextId: Int = -1,
    val isFavourite: Boolean = false
)