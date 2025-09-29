package com.dmitrysergeev.translateapp.domain.usecases.addfavouritewordtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

interface AddFavouriteWordTranslationUseCase {

    suspend operator fun invoke(worldTranslation: WordTranslation)

}