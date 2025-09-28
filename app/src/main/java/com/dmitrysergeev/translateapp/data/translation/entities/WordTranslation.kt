package com.dmitrysergeev.translateapp.data.translation.entities

import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity

data class WordTranslation(
    val id: Long,
    val originalWord: String,
    val translation: String
){

    fun toFavouriteDbEntity(): FavouriteDbEntity{
        return FavouriteDbEntity(
            id = this.id,
            baseWord = this.originalWord,
            translation = this.translation
        )
    }

    fun toHistoryDbEntity(): HistoryDbEntity{
        return HistoryDbEntity(
            id = this.id,
            baseWord = this.originalWord,
            translation = this.translation
        )
    }

}
