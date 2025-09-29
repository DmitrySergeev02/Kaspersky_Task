package com.dmitrysergeev.translateapp.domain.translation

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface FavouritesDataSource {

    fun getFavourites(): Flow<List<WordTranslation>>

    suspend fun addFavourite(wordTranslation: WordTranslation)

    suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation)

    fun getFavouriteByBaseWordAndTranslation(baseWord: String, translation: String): Flow<WordTranslation?>

}