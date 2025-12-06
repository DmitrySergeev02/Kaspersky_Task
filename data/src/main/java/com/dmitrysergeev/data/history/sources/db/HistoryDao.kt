package com.dmitrysergeev.data.history.sources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dmitrysergeev.data.history.sources.db.entities.HistoryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<HistoryDbEntity>>

    @Delete
    suspend fun deleteHistoryItem(historyDbEntity: HistoryDbEntity)
}