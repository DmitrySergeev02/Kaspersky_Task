package com.dmitrysergeev.translation.domain

interface TranslationRepository {
    suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): String
}