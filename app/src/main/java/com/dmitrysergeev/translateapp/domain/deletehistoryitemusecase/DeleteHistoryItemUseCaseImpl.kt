package com.dmitrysergeev.translateapp.domain.deletehistoryitemusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import javax.inject.Inject

class DeleteHistoryItemUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): DeleteHistoryItemUseCase {

    override suspend operator fun invoke(worldTranslation: WordTranslation){
        translationRepository.deleteHistoryItem(worldTranslation)
    }

}