package com.dmitrysergeev.translateapp.domain.gettranslationsforqueryusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetTranslationsForQueryUseCase {

    operator fun invoke(query: String): Flow<List<WordTranslation>>

}