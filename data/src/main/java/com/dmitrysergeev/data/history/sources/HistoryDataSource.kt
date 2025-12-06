package com.dmitrysergeev.data.history.sources

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getAllItemsFromHistory(): Flow<List<WordTranslationDataEntity>>

    suspend fun deleteFromHistory(wordTranslationDataEntity: WordTranslationDataEntity)
}