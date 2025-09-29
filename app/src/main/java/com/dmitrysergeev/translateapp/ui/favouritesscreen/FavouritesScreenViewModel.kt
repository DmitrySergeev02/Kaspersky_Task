package com.dmitrysergeev.translateapp.ui.favouritesscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.usecases.deletefavouritebybasewordandtranslationusecase.DeleteFavouriteByBaseWordAndTranslationUseCase
import com.dmitrysergeev.translateapp.domain.usecases.getfavouritetranslationsusecase.GetFavouriteTranslationsUseCase
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
class FavouritesScreenViewModel @Inject constructor(
    private val getFavouriteTranslationsUseCase: GetFavouriteTranslationsUseCase,
    private val deleteFavouriteByBaseWordAndTranslationUseCase: DeleteFavouriteByBaseWordAndTranslationUseCase
): ViewModel() {

    private val _favouritesItems: MutableStateFlow<List<WordTranslation>> = MutableStateFlow(emptyList())
    val favouriteItems: StateFlow<List<WordTranslation>> = _favouritesItems.asStateFlow()

    private val _uiState: MutableStateFlow<FavouritesScreenUiState> = MutableStateFlow(FavouritesScreenUiState())
    val uiState: StateFlow<FavouritesScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getFavouriteTranslationsUseCase()
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
                    _favouritesItems.value = emptyList()
                }
                .collect { items->
                    _uiState.update { oldState->
                        oldState.copy(
                            snackbarTextId = if (items.isEmpty()) R.string.empty_favourites else -1,
                            isLoading = false
                        )
                    }
                    _favouritesItems.value = items.reversed()
                }
        }
    }

    fun deleteFromFavourites(itemToDelete: WordTranslation){
        viewModelScope.launch {
            deleteFavouriteByBaseWordAndTranslationUseCase(
                BaseWordAndTranslation(
                    baseWord = itemToDelete.originalWord.lowercase(),
                    translation = itemToDelete.translation
                )
            )
        }
    }

    companion object{
        const val TAG = "FavouritesScreenViewModelTag"
    }
}