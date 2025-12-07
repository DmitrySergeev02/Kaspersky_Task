package com.dmitrysergeev.translateapp.domain.usecases

import com.dmitrysergeev.translateapp.di.IoDispatcher
import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
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