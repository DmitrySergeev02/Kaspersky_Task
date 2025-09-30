package com.dmitrysergeev.translateapp.ui.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.usecases.addfavouritewordtranslationusecase.AddFavouriteWordTranslationUseCase
import com.dmitrysergeev.translateapp.domain.usecases.addhistoryitemusecase.AddHistoryItemUseCase
import com.dmitrysergeev.translateapp.domain.usecases.deletefavouritebybasewordandtranslationusecase.DeleteFavouriteByBaseWordAndTranslationUseCase
import com.dmitrysergeev.translateapp.domain.usecases.deletehistoryitemusecase.DeleteHistoryItemUseCase
import com.dmitrysergeev.translateapp.domain.usecases.getfavouritebybasewordandtranslationusecase.GetFavouriteByBaseWordAndTranslationUseCase
import com.dmitrysergeev.translateapp.domain.usecases.gethistoryusecase.GetHistoryUseCase
import com.dmitrysergeev.translateapp.domain.usecases.gettranslationsforqueryusecase.GetTranslationsForQueryUseCase
import com.dmitrysergeev.translateapp.utils.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getTranslationsForQueryUseCase: GetTranslationsForQueryUseCase,
    private val getFavouriteByBaseWordAndTranslationUseCase: GetFavouriteByBaseWordAndTranslationUseCase,
    private val addHistoryItemUseCase: AddHistoryItemUseCase,
    private val deleteHistoryItemUseCase: DeleteHistoryItemUseCase,
    private val addFavouriteWordTranslationUseCase: AddFavouriteWordTranslationUseCase,
    private val deleteFavouriteByBaseWordAndTranslationUseCase: DeleteFavouriteByBaseWordAndTranslationUseCase
): ViewModel() {

    private val _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState())
    val mainScreenUiState = _mainScreenUiState.asStateFlow()

    private val _historyItemsState: MutableStateFlow<List<WordTranslation>> = MutableStateFlow(emptyList())
    val historyItemsState = _historyItemsState.asStateFlow()

    private var currentInput: String = ""

    init {
        viewModelScope.launch {
            getHistoryUseCase()
                .collect{ historyItems ->
                    _historyItemsState.value = historyItems.reversed()
                }
        }
    }

    private var checkFavouriteJob: Job? = null

    fun translateText(query: String){
        checkFavouriteJob?.cancel()
        val trimmedString = query.trim()
        if (InputValidator.isCorrect(trimmedString)){
            checkFavouriteJob = viewModelScope.launch {
                getTranslationsForQueryUseCase(trimmedString)
                    .onStart {
                        _mainScreenUiState.update { oldState->
                            oldState.copy(
                                isLoading = true,
                                snackbarTextId = -1
                            )
                        }
                    }
                    .catch { error->
                        _mainScreenUiState.value = MainScreenUiState(
                            snackbarTextId = R.string.network_error_try_again_later
                        )
                        Log.d(TAG, error.message ?: "unknown error")
                    }
                    .collect{ words->
                        if (words.isEmpty()){
                            _mainScreenUiState.value = MainScreenUiState(
                                snackbarTextId = R.string.network_error_no_translation,
                                isLoading = false
                            )
                        } else {
                            currentInput = trimmedString
                            _mainScreenUiState.value = MainScreenUiState(
                                translateResult = words[0].translation,
                                isLoading = false
                            )
                            getFavouriteByBaseWordAndTranslationUseCase(
                                    baseWord = currentInput,
                                    translation = words[0].translation
                                )
                                .catch { error ->
                                    Log.d(TAG, error.message ?: "Unknown Error")
                                    _mainScreenUiState.update { oldState->
                                        oldState.copy(
                                            snackbarTextId = R.string.check_favourites_error,
                                            isFavourite = false
                                        )
                                    }
                                }
                                .onStart {
                                    addHistoryItemUseCase(
                                        WordTranslation(
                                            id = 0,
                                            originalWord = trimmedString,
                                            translation = words[0].translation
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
                snackbarTextId = R.string.network_error_not_english
            )
        }
    }

    fun deleteItemFromHistory(wordTranslation: WordTranslation){
        viewModelScope.launch {
            deleteHistoryItemUseCase(wordTranslation)
        }
    }

    fun changeFavouriteState(isFavourite: Boolean){
        viewModelScope.launch {
            if (isFavourite){
                addFavouriteWordTranslationUseCase(
                    WordTranslation(
                        id = 0,
                        originalWord = currentInput,
                        translation = _mainScreenUiState.value.translateResult
                    )
                )
            } else {
                deleteFavouriteByBaseWordAndTranslationUseCase(
                    BaseWordAndTranslation(
                        baseWord = currentInput.lowercase(),
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