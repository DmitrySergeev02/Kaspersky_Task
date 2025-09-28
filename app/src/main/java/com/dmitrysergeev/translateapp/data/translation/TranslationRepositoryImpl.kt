package com.dmitrysergeev.translateapp.data.translation

import com.dmitrysergeev.translateapp.data.translation.db.DbTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.data.translation.network.NetworkTranslationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkTranslationRepository,
    private val dbDataSource: DbTranslationRepository
): TranslationRepository {
    override fun getTranslations(query: String): Flow<List<WordTranslation>> = networkDataSource.getTranslations(query)

    override fun getFavourites(): Flow<List<WordTranslation>> = dbDataSource.getFavourites()

    override suspend fun addFavourite(wordTranslation: WordTranslation) = dbDataSource.addFavourite(wordTranslation)

    override suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation) = dbDataSource.deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation)

    override fun getFavouriteByBaseWordAndTranslation(
        baseWord: String,
        translation: String
    ): Flow<WordTranslation?> = dbDataSource.getFavouriteByBaseWordAndTranslation(baseWord, translation)

    override fun getHistory(): Flow<List<WordTranslation>> = dbDataSource.getHistory()

    override suspend fun addHistoryItem(wordTranslation: WordTranslation) = dbDataSource.addHistoryItem(wordTranslation)

    override suspend fun deleteHistoryItem(wordTranslation: WordTranslation) = dbDataSource.deleteHistoryItem(wordTranslation)
}