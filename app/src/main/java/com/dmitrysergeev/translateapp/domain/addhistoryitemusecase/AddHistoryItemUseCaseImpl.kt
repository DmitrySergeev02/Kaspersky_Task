package com.dmitrysergeev.translateapp.domain.addhistoryitemusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import javax.inject.Inject

class AddHistoryItemUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): AddHistoryItemUseCase {

    override suspend operator fun invoke(worldTranslation: WordTranslation){
        translationRepository.addHistoryItem(worldTranslation)
    }

}