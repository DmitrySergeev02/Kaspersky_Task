package com.dmitrysergeev.translateapp.data.translation.db.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

@Entity(
    tableName = "history"
)
data class HistoryDbEntity(
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