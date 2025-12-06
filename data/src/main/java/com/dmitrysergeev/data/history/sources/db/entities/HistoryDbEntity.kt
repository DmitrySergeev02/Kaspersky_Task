package com.dmitrysergeev.data.history.sources.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "history"
)
data class HistoryDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "from_language") val fromLanguage: String,
    @ColumnInfo(name = "to_language") val toLanguage: String,
    val input: String,
    val output: String
)