package com.dmitrysergeev.translateapp.data.translation.db.favourites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

@Entity(
    tableName = "favourites"
)
data class FavouriteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val baseWord: String,
    val translation: String
){

    fun toWordTranslation(): WordTranslation{
        return WordTranslation(
            id = this.id,
            originalWord = this.baseWord,
            translation = this.translation
        )
    }

}

data class BaseWordAndTranslation(
    val baseWord: String,
    val translation: String
)