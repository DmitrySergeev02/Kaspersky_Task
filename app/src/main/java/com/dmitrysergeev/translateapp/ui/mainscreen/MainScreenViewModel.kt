package com.dmitrysergeev.translateapp.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.domain.usecases.GetTranslationForQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getTranslationForQueryUseCase: GetTranslationForQueryUseCase
): ViewModel() {

    private val _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState())
    val mainScreenUiState = _mainScreenUiState.asStateFlow()

    private val _currentInput: MutableStateFlow<String> = MutableStateFlow("")
    val currentInput = _currentInput.asStateFlow()

    private fun updateUiState(
        translationResult: String? = null,
        snackBarTextId: Int? = null,
        isLoading: Boolean? = null
    ){
        _mainScreenUiState.update { oldState->
            oldState.copy(
                translateResult = translationResult ?: oldState.translateResult,
                snackbarTextId = snackBarTextId ?: oldState.snackbarTextId,
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
                updateUiState(isLoading = false, snackBarTextId = R.string.network_error_try_again_later)
            }

        }
    }

    companion object{
        const val TAG = "MainScreenViewModelTag"
    }
}