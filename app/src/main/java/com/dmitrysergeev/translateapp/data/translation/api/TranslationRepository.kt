package com.dmitrysergeev.translateapp.data.translation.api

import com.dmitrysergeev.translateapp.data.translation.api.model.WordApi
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {

    fun getTranslations(query: String): Flow<List<WordApi>>

}