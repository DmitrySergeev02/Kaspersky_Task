package com.dmitrysergeev.data.translation.sources

interface TranslationDataSource {
    suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): String
}