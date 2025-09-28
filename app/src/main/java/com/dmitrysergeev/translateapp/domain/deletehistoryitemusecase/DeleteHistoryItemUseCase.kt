package com.dmitrysergeev.translateapp.domain.deletehistoryitemusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

interface DeleteHistoryItemUseCase {

    suspend operator fun invoke(worldTranslation: WordTranslation)

}