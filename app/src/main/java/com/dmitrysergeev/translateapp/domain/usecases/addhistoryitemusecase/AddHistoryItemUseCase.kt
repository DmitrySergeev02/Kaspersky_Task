package com.dmitrysergeev.translateapp.domain.usecases.addhistoryitemusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

interface AddHistoryItemUseCase {

    suspend operator fun invoke(worldTranslation: WordTranslation)

}