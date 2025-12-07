package com.dmitrysergeev.translateapp.domain.history

import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAllHistory(): Flow<List<WordTranslation>>

    suspend fun deleteFromHistory(wordTranslation: WordTranslation)
}