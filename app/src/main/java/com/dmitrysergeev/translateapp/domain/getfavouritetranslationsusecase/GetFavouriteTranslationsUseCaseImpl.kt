package com.dmitrysergeev.translateapp.domain.getfavouritetranslationsusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteTranslationsUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): GetFavouriteTranslationsUseCase {

    override operator fun invoke(): Flow<List<WordTranslation>> {
        return translationRepository.getFavourites()
    }

}