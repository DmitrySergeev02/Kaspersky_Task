package com.dmitrysergeev.translation.domain

import com.dmitrysergeev.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTranslationForQueryUseCase @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(fromLanguage: String, toLanguage: String, input: String): String = withContext(dispatcher){
        translationRepository.getTranslation(fromLanguage, toLanguage, input)
    }

}