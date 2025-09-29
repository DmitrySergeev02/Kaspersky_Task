package com.dmitrysergeev.translateapp.domain.usecases.deletefavouritebybasewordandtranslationusecase

import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteFavouriteByBaseWordAndTranslationUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): DeleteFavouriteByBaseWordAndTranslationUseCase {

    override suspend operator fun invoke(baseWordAndTranslation: BaseWordAndTranslation) = withContext(dispatcher){
        translationRepository.deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation)
    }

}