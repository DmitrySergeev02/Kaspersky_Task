package com.dmitrysergeev.translateapp.data.translation.db

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import kotlinx.coroutines.flow.Flow

interface TranslationDbRepository {

    fun getFavourites(): Flow<List<FavouriteDbEntity>>

    suspend fun addFavourite(favouriteDbEntity: FavouriteDbEntity)

    suspend fun deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation: BaseWordAndTranslation)

    fun getFavouriteByBaseWordAndTranslation(baseWord: String, translation: String): Flow<FavouriteDbEntity?>

    fun getHistory(): Flow<List<HistoryDbEntity>>

    suspend fun addHistoryItem(historyDbEntity: HistoryDbEntity)

    suspend fun deleteHistoryItem(historyDbEntity: HistoryDbEntity)

}