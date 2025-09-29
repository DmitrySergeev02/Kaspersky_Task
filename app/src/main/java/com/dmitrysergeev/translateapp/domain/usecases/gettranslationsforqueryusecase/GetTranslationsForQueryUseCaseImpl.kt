package com.dmitrysergeev.translateapp.domain.usecases.gettranslationsforqueryusecase

import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTranslationsForQueryUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): GetTranslationsForQueryUseCase {

    override operator fun invoke(query: String): Flow<List<WordTranslation>> {
        return translationRepository
            .getTranslations(query)
            .flowOn(dispatcher)
    }

}