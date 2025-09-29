package com.dmitrysergeev.translateapp.domain.usecases.getfavouritetranslationsusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetFavouriteTranslationsUseCase {

    operator fun invoke(): Flow<List<WordTranslation>>

}