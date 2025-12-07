package com.dmitrysergeev.translation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.core.R as  R_base
import com.dmitrysergeev.translation.domain.GetTranslationForQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslationScreenViewModel @Inject constructor(
    private val getTranslationForQueryUseCase: GetTranslationForQueryUseCase
): ViewModel() {

    private val _translationScreenUiState: MutableStateFlow<TranslationScreenUiState> = MutableStateFlow(
        TranslationScreenUiState()
    )
    val mainScreenUiState = _translationScreenUiState.asStateFlow()

    private val _currentInput: MutableStateFlow<String> = MutableStateFlow("")
    val currentInput = _currentInput.asStateFlow()

    private fun updateUiState(
        translationResult: String? = null,
        snackBarTextId: Int? = null,
        isLoading: Boolean? = null
    ){
        _translationScreenUiState.update { oldState->
            oldState.copy(
                translateResult = translationResult ?: oldState.translateResult,
                snackbarTextId = snackBarTextId ?: -1,
                isLoading = isLoading ?: oldState.isLoading
            )
        }
    }

    fun updateInput(newValue: String){
        _currentInput.update {
            newValue
        }
    }

    fun translateText(){
        updateUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val translationResult = getTranslationForQueryUseCase("ru","en", _currentInput.value)
                updateUiState(isLoading = false, translationResult = translationResult)
            } catch (e: Exception){
                updateUiState(isLoading = false, snackBarTextId = R_base.string.network_error_try_again_later)
            }

        }
    }

    companion object{
        const val TAG = "MainScreenViewModelTag"
    }
}