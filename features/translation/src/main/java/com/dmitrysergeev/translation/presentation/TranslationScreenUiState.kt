package com.dmitrysergeev.translation.presentation

data class TranslationScreenUiState(
    val translateResult: String = "",
    val snackbarTextId: Int = -1,
    val isLoading: Boolean = false
)