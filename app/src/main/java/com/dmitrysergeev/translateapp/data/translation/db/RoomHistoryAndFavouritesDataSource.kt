package com.dmitrysergeev.translateapp.data.translation.db

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.translation.FavouritesDataSource
import com.dmitrysergeev.translateapp.domain.translation.HistoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomHistoryAndFavouritesDataSource @Inject constructor(
    private val database: TranslateDatabase
): HistoryDataSource, FavouritesDataSource {

    override fun getFavourites(): Flow<List<WordTranslation>> = database.favouriteDao()
        .getFavourites()
        .map { list-> list.map { item -> item.toWordTranslation() } }

    override suspend fun addFavourite(wordTranslation: WordTranslation) = database.favouriteDao()
        .addFavourite(wordTranslation.toFavouriteDbEntity())

    override suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation) = database.favouriteDao().deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation)

    override fun getFavouriteByBaseWordAndTranslation(baseWord: String, translation: String) = database.favouriteDao()
        .getFavouriteByBaseWordAndTranslation(baseWord, translation)
        .map { it?.toWordTranslation() }

    override fun getHistory(): Flow<List<WordTranslation>> = database.historyDao()
        .getHistory()
        .map { list-> list.map { item-> item.toWordTranslation() } }

    override suspend fun addHistoryItem(wordTranslation: WordTranslation) = database.historyDao()
        .addHistoryItem(wordTranslation.toHistoryDbEntity())

    override suspend fun deleteHistoryItem(wordTranslation: WordTranslation) = database.historyDao()
        .deleteHistoryItem(wordTranslation.toHistoryDbEntity())
}