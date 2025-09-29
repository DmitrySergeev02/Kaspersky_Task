package com.dmitrysergeev.translateapp.domain.usecases.getfavouritebybasewordandtranslationusecase

import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavouriteByBaseWordAndTranslationUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): GetFavouriteByBaseWordAndTranslationUseCase {

    override operator fun invoke(baseWord: String, translation: String): Flow<WordTranslation?> {
        return translationRepository
            .getFavouriteByBaseWordAndTranslation(baseWord,translation)
            .flowOn(dispatcher)
    }

}