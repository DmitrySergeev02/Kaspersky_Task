package com.dmitrysergeev.translateapp.glue.translation.repositories

import com.dmitrysergeev.data.TranslationDataRepository
import com.dmitrysergeev.translation.domain.TranslationRepository
import javax.inject.Inject

class AdapterTranslationRepository @Inject constructor(
    private val translationDataRepository: TranslationDataRepository
): TranslationRepository {
    override suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): Result<String>
        = translationDataRepository.getTranslation(fromLanguage,toLanguage, input)
}