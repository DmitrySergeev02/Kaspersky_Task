package com.dmitrysergeev.translateapp.data.translation.db

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface TranslationDbRepository {

    fun getFavourites(): Flow<List<WordTranslation>>

    suspend fun addFavourite(wordTranslation: WordTranslation)

    suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation)

    fun getFavouriteByBaseWordAndTranslation(baseWord: String, translation: String): Flow<WordTranslation?>

    fun getHistory(): Flow<List<WordTranslation>>

    suspend fun addHistoryItem(wordTranslation: WordTranslation)

    suspend fun deleteHistoryItem(wordTranslation: WordTranslation)

}