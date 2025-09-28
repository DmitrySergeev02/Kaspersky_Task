package com.dmitrysergeev.translateapp.domain.gethistoryusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): GetHistoryUseCase {

    override operator fun invoke(): Flow<List<WordTranslation>> {
        return translationRepository.getHistory()
    }

}