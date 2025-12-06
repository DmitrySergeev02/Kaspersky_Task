package com.dmitrysergeev.data.translation

import com.dmitrysergeev.data.TranslationDataRepository
import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.data.translation.sources.MutableTranslationDataSource
import com.dmitrysergeev.data.translation.sources.TranslationDataSource
import javax.inject.Inject

class RealTranslationDataRepository @Inject constructor(
    private val dbTranslationDataSource: MutableTranslationDataSource,
    private val apiTranslationDataSource: TranslationDataSource
): TranslationDataRepository {
    override suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): String {
        val translationFromDb = dbTranslationDataSource.getTranslation(fromLanguage, toLanguage, input)
        if (translationFromDb!="-1")
            return translationFromDb

        val translationFromApi = apiTranslationDataSource.getTranslation(fromLanguage, toLanguage, input)
        dbTranslationDataSource.addToHistory(WordTranslationDataEntity(
            0,
            fromLanguage,
            toLanguage,
            input,
             translationFromApi
        ))

        return translationFromApi
    }
}