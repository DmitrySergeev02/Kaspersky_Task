package com.dmitrysergeev.translateapp.domain.translation

interface TranslationRepository {
    suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): String
}