package com.dmitrysergeev.translateapp.ui.historyscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.usecases.DeleteHistoryItemUseCase
import com.dmitrysergeev.translateapp.domain.usecases.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryItemUseCase: DeleteHistoryItemUseCase
): ViewModel() {

    private val _historyItems: MutableStateFlow<List<WordTranslation>> = MutableStateFlow(emptyList())
    val historyItems: StateFlow<List<WordTranslation>> = _historyItems.asStateFlow()

    private val _uiState: MutableStateFlow<HistoryScreenUiState> = MutableStateFlow(HistoryScreenUiState())
    val uiState: StateFlow<HistoryScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getHistoryUseCase()
                .onStart {
                    _uiState.update { oldState->
                        oldState.copy(snackbarTextId = -1, isLoading = true)
                    }
                }
                .catch { error->
                    _uiState.update { oldState->
                        oldState.copy(snackbarTextId = R.string.check_favourites_error, isLoading = false)
                    }
                    Log.d(TAG, error.message ?: "Unknown Error")
                    _historyItems.value = emptyList()
                }
                .collect { items->
                    _uiState.update { oldState->
                        oldState.copy(
                            snackbarTextId = if (items.isEmpty()) R.string.empty_favourites else -1,
                            isLoading = false
                        )
                    }
                    _historyItems.value = items.reversed()
                }
        }
    }

    fun deleteFromFavourites(itemToDelete: WordTranslation){
        viewModelScope.launch {
            deleteHistoryItemUseCase(
                itemToDelete
            )
        }
    }

    companion object{
        const val TAG = "FavouritesScreenViewModelTag"
    }
}