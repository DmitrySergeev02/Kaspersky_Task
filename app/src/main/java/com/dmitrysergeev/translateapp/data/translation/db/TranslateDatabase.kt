package com.dmitrysergeev.translateapp.data.translation.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDao
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDao
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity

@Database(
    entities = [
        HistoryDbEntity::class,
        FavouriteDbEntity::class
    ],
    version = 1
)
abstract class TranslateDatabase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    abstract fun favouriteDao(): FavouriteDao

}