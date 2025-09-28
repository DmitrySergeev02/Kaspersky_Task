package com.dmitrysergeev.translateapp.data.translation

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {

    fun getTranslations(query: String): Flow<List<WordTranslation>>

    fun getFavourites(): Flow<List<WordTranslation>>

    suspend fun addFavourite(wordTranslation: WordTranslation)

    suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation)

    fun getFavouriteByBaseWordAndTranslation(baseWord: String, translation: String): Flow<WordTranslation?>

    fun getHistory(): Flow<List<WordTranslation>>

    suspend fun addHistoryItem(wordTranslation: WordTranslation)

    suspend fun deleteHistoryItem(wordTranslation: WordTranslation)

}