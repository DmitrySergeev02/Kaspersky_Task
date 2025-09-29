package com.dmitrysergeev.translateapp.domain.usecases.deletefavouritebybasewordandtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation

interface DeleteFavouriteByBaseWordAndTranslationUseCase {

    suspend operator fun invoke(baseWordAndTranslation: BaseWordAndTranslation)

}