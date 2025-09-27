package com.dmitrysergeev.translateapp.data.translation.db.favourites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourites"
)
data class FavouriteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val baseWord: String,
    val translation: String
)

data class BaseWordAndTranslation(
    val baseWord: String,
    val translation: String
)