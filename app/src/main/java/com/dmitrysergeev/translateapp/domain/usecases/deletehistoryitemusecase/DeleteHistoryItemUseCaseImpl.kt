package com.dmitrysergeev.translateapp.domain.usecases.deletehistoryitemusecase

import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHistoryItemUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): DeleteHistoryItemUseCase {

    override suspend operator fun invoke(worldTranslation: WordTranslation) = withContext(dispatcher){
        translationRepository.deleteHistoryItem(worldTranslation)
    }

}