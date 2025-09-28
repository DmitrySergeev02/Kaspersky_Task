package com.dmitrysergeev.translateapp.domain.addhistoryitemusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

interface AddHistoryItemUseCase {

    suspend operator fun invoke(worldTranslation: WordTranslation)

}