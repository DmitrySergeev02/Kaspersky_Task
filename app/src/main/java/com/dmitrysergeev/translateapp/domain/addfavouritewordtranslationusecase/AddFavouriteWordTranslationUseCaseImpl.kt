package com.dmitrysergeev.translateapp.domain.addfavouritewordtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import javax.inject.Inject

class AddFavouriteWordTranslationUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): AddFavouriteWordTranslationUseCase {

    override suspend operator fun invoke(worldTranslation:WordTranslation){
        translationRepository.addFavourite(worldTranslation)
    }

}