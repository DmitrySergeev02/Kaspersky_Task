package com.dmitrysergeev.translateapp.data.translation.db.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "history"
)
data class HistoryDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val baseWord: String,
    val translation: String
)