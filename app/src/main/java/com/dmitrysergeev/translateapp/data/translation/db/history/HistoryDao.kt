package com.dmitrysergeev.translateapp.data.translation.db.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<HistoryDbEntity>>

    @Insert
    suspend fun addHistoryItem(historyDbEntity: HistoryDbEntity)

    @Delete
    suspend fun deleteHistoryItem(historyDbEntity: HistoryDbEntity)
}