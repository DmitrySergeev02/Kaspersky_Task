package com.dmitrysergeev.translateapp.domain.getfavouritebybasewordandtranslationusecase

import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteByBaseWordAndTranslationUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository
): GetFavouriteByBaseWordAndTranslationUseCase {

    override operator fun invoke(baseWord: String, translation: String): Flow<WordTranslation?> {
        return translationRepository.getFavouriteByBaseWordAndTranslation(baseWord,translation)
    }

}