package com.dmitrysergeev.history.domain

import com.dmitrysergeev.history.domain.entities.HistoryTranslation
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAllHistory(): Flow<List<HistoryTranslation>>

    suspend fun deleteFromHistory(historyTranslation: HistoryTranslation)
}