package com.dmitrysergeev.translateapp.ui.favouritesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.data.translation.db.TranslationDbRepository
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
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

    private val _favouritesItems: MutableStateFlow<List<FavouriteDbEntity>> = MutableStateFlow(emptyList())
    val favouriteItems: StateFlow<List<FavouriteDbEntity>> = _favouritesItems.asStateFlow()

    init {
        viewModelScope.launch {
            translationDbRepository.getFavourites()
                .catch {  }
                .collect { items->
                    _favouritesItems.value = items
                }
        }
    }


}