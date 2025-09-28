package com.dmitrysergeev.translateapp.data.translation.network

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface NetworkTranslationRepository {

    fun getTranslations(query: String): Flow<List<WordTranslation>>

}