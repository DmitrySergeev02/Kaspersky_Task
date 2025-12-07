package com.dmitrysergeev.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitrysergeev.data.history.sources.db.HistoryDao
import com.dmitrysergeev.data.history.sources.db.entities.HistoryDbEntity
import com.dmitrysergeev.data.translation.sources.db.TranslationDao

@Database(
    entities = [
        HistoryDbEntity::class,
    ],
    version = 1
)
abstract class TranslateDatabase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    abstract fun translationDao(): TranslationDao

}