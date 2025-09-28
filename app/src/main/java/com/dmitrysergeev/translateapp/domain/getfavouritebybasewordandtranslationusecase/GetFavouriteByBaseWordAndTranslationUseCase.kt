package com.dmitrysergeev.translateapp.domain.getfavouritebybasewordandtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetFavouriteByBaseWordAndTranslationUseCase {

    operator fun invoke(baseWord: String, translation: String): Flow<WordTranslation?>

}