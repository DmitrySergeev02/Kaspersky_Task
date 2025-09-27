package com.dmitrysergeev.translateapp.data.translation.db

import android.content.Context
import androidx.room.Room
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "translation_database"

class RoomTranslationDbRepository(
    context: Context
): TranslationDbRepository {

    private val database: TranslateDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TranslateDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    override fun getFavourites(): Flow<List<FavouriteDbEntity>> = database.favouriteDao().getFavourites()

    override suspend fun addFavourite(favouriteDbEntity: FavouriteDbEntity) = database.favouriteDao().addFavourite(favouriteDbEntity)

    override suspend fun deleteFavourite(favouriteDbEntity: FavouriteDbEntity) = database.favouriteDao().deleteFavourite(favouriteDbEntity)

    override fun getHistory(): Flow<List<HistoryDbEntity>> = database.historyDao().getHistory()

    override suspend fun addHistoryItem(historyDbEntity: HistoryDbEntity) = database.historyDao().addHistoryItem(historyDbEntity)

    override suspend fun deleteHistoryItem(historyDbEntity: HistoryDbEntity) = database.historyDao().deleteHistoryItem(historyDbEntity)
}