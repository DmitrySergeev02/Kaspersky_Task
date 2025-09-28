package com.dmitrysergeev.translateapp.ui.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.data.translation.api.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.TranslationDbRepository
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import com.dmitrysergeev.translateapp.utils.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val translationRepository: TranslationRepository,
    private val translationDbRepository: TranslationDbRepository
): ViewModel() {

    private val _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState())
    val mainScreenUiState = _mainScreenUiState.asStateFlow()

    private val _historyItemsState: MutableStateFlow<List<HistoryDbEntity>> = MutableStateFlow(emptyList())
    val historyItemsState = _historyItemsState.asStateFlow()

    private var currentInput: String = ""

    init {
        viewModelScope.launch {
            translationDbRepository.getHistory()
                .collect{ historyItems ->
                    _historyItemsState.value = historyItems
                }
        }
    }

    fun translateText(query: String){
        val trimmedString = query.trim()
        if (InputValidator.isCorrect(trimmedString)){
            viewModelScope.launch {
                translationRepository.getTranslations(query)
                    .catch { error->
                        _mainScreenUiState.value = MainScreenUiState(
                            snackbarText = "Во время запроса произошла ошибка, повторите попытку позже"
                        )
                        Log.d(TAG, error.message ?: "unknown error")
                    }
                    .collect{ words->
                        if (words.isEmpty()){
                            _mainScreenUiState.value = MainScreenUiState(
                                snackbarText ="Для введённого слова не найден перевод"
                            )
                        } else {
                            currentInput = trimmedString
                            _mainScreenUiState.value = MainScreenUiState(
                                translateResult = words[0].text,
                            )
                            translationDbRepository
                                .getFavouriteByBaseWordAndTranslation(
                                    baseWord = currentInput,
                                    translation = words[0].text
                                )
                                .catch { error ->
                                    Log.d(TAG, error.message ?: "Unknown Error")
                                }
                                .onStart {
                                    translationDbRepository.addHistoryItem(
                                        HistoryDbEntity(
                                            id = 0,
                                            baseWord = trimmedString,
                                            translation = words[0].text
                                        )
                                    )
                                }
                                .collect { result->
                                    _mainScreenUiState.update { oldState->
                                        oldState.copy(isFavourite = (result!=null))
                                    }
                                }
                        }
                    }
            }
        } else {
            _mainScreenUiState.value = MainScreenUiState(
                snackbarText = "Введите одно корректное слово на русском языке"
            )
        }
    }

    fun deleteItemFromHistory(historyDbEntity: HistoryDbEntity){
        viewModelScope.launch {
            translationDbRepository.deleteHistoryItem(historyDbEntity)
        }
    }

    fun changeFavouriteState(isFavourite: Boolean){
        _mainScreenUiState.update { state->
            state.copy(isFavourite = isFavourite)
        }
        viewModelScope.launch {
            if (isFavourite){
                translationDbRepository.addFavourite(
                    FavouriteDbEntity(
                        id = 0,
                        baseWord = currentInput,
                        translation = _mainScreenUiState.value.translateResult
                    )
                )
            } else {
                translationDbRepository.deleteFavouriteByBaseWordAndTranslation(
                    BaseWordAndTranslation(
                        baseWord = currentInput,
                        translation = _mainScreenUiState.value.translateResult
                    )
                )
            }
        }
    }

    companion object{
        const val TAG = "MainScreenViewModelTag"
    }
}