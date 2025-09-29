package com.dmitrysergeev.translateapp.domain.translation

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {

    fun getHistory(): Flow<List<WordTranslation>>

    suspend fun addHistoryItem(wordTranslation: WordTranslation)

    suspend fun deleteHistoryItem(wordTranslation: WordTranslation)

}