package com.dmitrysergeev.translateapp.ui.favouritesscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.data.translation.db.TranslationDbRepository
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesScreenViewModel @Inject constructor(
    private val translationDbRepository: TranslationDbRepository
): ViewModel() {

    private val _favouritesItems: MutableStateFlow<List<WordTranslation>> = MutableStateFlow(emptyList())
    val favouriteItems: StateFlow<List<WordTranslation>> = _favouritesItems.asStateFlow()

    init {
        viewModelScope.launch {
            translationDbRepository.getFavourites()
                .catch { error->
                    Log.d(TAG, error.message ?: "Unknown Error")
                    _favouritesItems.value = emptyList()
                }
                .collect { items->
                    _favouritesItems.value = items.reversed()
                }
        }
    }

    fun deleteFromFavourites(itemToDelete: WordTranslation){
        viewModelScope.launch {
            translationDbRepository.deleteFavouriteByBaseWordAndTranslation(
                BaseWordAndTranslation(
                    baseWord = itemToDelete.originalWord,
                    translation = itemToDelete.translation
                )
            )
        }
    }

    companion object{
        const val TAG = "FavouritesScreenViewModelTag"
    }
}