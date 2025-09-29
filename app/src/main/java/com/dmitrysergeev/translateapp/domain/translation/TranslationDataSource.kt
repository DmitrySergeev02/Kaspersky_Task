package com.dmitrysergeev.translateapp.domain.translation

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface TranslationDataSource {
    fun getTranslations(query: String): Flow<List<WordTranslation>>
}