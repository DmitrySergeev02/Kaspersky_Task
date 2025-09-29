package com.dmitrysergeev.translateapp.domain.translation

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TranslationRepository @Inject constructor(
    private val translationDataSource: TranslationDataSource,
    private val historyDataSource: HistoryDataSource,
    private val favouritesDataSource: FavouritesDataSource
) {
    fun getTranslations(query: String): Flow<List<WordTranslation>> = translationDataSource.getTranslations(query)

    fun getFavourites(): Flow<List<WordTranslation>> = favouritesDataSource.getFavourites()

    suspend fun addFavourite(wordTranslation: WordTranslation) = favouritesDataSource.addFavourite(wordTranslation)

    suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation) = favouritesDataSource.deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation)

    fun getFavouriteByBaseWordAndTranslation(
        baseWord: String,
        translation: String
    ): Flow<WordTranslation?> = favouritesDataSource.getFavouriteByBaseWordAndTranslation(baseWord, translation)

    fun getHistory(): Flow<List<WordTranslation>> = historyDataSource.getHistory()

    suspend fun addHistoryItem(wordTranslation: WordTranslation) = historyDataSource.addHistoryItem(wordTranslation)

    suspend fun deleteHistoryItem(wordTranslation: WordTranslation) = historyDataSource.deleteHistoryItem(wordTranslation)
}