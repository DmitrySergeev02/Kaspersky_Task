package com.dmitrysergeev.translateapp.domain.gethistoryusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetHistoryUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): GetHistoryUseCase {

    override operator fun invoke(): Flow<List<WordTranslation>> {
        return translationRepository
            .getHistory()
            .flowOn(dispatcher)
    }

}