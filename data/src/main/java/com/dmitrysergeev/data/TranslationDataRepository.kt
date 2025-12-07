package com.dmitrysergeev.data

interface TranslationDataRepository {
    suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): Result<String>
}