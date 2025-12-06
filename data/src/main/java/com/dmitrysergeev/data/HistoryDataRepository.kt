package com.dmitrysergeev.data

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import kotlinx.coroutines.flow.Flow

interface HistoryDataRepository {
    fun getAllHistory(): Flow<List<WordTranslationDataEntity>>

    suspend fun deleteFromHistory(wordTranslationDataEntity: WordTranslationDataEntity)
}