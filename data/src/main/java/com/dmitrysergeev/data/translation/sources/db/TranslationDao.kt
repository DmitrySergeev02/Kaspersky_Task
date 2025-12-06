package com.dmitrysergeev.data.translation.sources.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dmitrysergeev.data.history.sources.db.entities.HistoryDbEntity
@Dao
interface TranslationDao {

    @Query("SELECT * FROM history WHERE from_language LIKE :fromLanguage AND to_language LIKE :toLanguage AND input LIKE :input")
    suspend fun getTranslationWhereInput(fromLanguage: String, toLanguage: String, input: String): HistoryDbEntity?

    @Query("SELECT * FROM history WHERE from_language LIKE :fromLanguage AND to_language LIKE :toLanguage AND output LIKE :output")
    suspend fun getTranslationWhereOutput(fromLanguage: String, toLanguage: String, output: String): HistoryDbEntity?

    @Insert
    suspend fun addToHistory(historyDbEntity: HistoryDbEntity)

    @Transaction
    suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): String{
        val translationWhereInput: HistoryDbEntity? = getTranslationWhereInput(
            fromLanguage,
            toLanguage,
            input
        )

        translationWhereInput?.let {
            return it.output
        }

        val translationWhereOutput: HistoryDbEntity? = getTranslationWhereOutput(
            toLanguage,
            fromLanguage,
            input
        )

        return translationWhereOutput?.output ?: "-1"
    }
}