package com.dmitrysergeev.data.history.sources.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dmitrysergeev.data.history.sources.db.entities.HistoryDbEntity
import com.dmitrysergeev.data.history.sources.db.entities.IdToDelete
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<HistoryDbEntity>>

    @Delete(HistoryDbEntity::class)
    suspend fun deleteHistoryItem(idToDelete: IdToDelete)
}