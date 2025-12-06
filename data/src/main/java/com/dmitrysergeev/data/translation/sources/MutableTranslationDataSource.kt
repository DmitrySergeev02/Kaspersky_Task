package com.dmitrysergeev.data.translation.sources

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity

interface MutableTranslationDataSource: TranslationDataSource {
    suspend fun addToHistory(wordTranslationDataEntity: WordTranslationDataEntity)
}