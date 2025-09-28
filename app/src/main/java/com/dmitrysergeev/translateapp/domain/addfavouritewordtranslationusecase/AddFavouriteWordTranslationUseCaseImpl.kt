package com.dmitrysergeev.translateapp.domain.addfavouritewordtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddFavouriteWordTranslationUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): AddFavouriteWordTranslationUseCase {

    override suspend operator fun invoke(worldTranslation:WordTranslation) = withContext(dispatcher){
        translationRepository.addFavourite(worldTranslation)
    }

}