package com.dmitrysergeev.translateapp.domain.gettranslationsforqueryusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTranslationsForQueryUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): GetTranslationsForQueryUseCase {

    override operator fun invoke(query: String): Flow<List<WordTranslation>> {
        return translationRepository.getTranslations(query)
    }

}