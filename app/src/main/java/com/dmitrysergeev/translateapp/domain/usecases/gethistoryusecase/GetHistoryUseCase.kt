package com.dmitrysergeev.translateapp.domain.usecases.gethistoryusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetHistoryUseCase {

    operator fun invoke(): Flow<List<WordTranslation>>

}