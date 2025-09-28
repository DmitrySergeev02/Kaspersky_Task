package com.dmitrysergeev.translateapp.domain.deletefavouritebybasewordandtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import javax.inject.Inject

class DeleteFavouriteByBaseWordAndTranslationUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): DeleteFavouriteByBaseWordAndTranslationUseCase {

    override suspend operator fun invoke(baseWordAndTranslation: BaseWordAndTranslation){
        translationRepository.deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation)
    }

}