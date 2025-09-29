package com.dmitrysergeev.translateapp.domain.usecases.getfavouritetranslationsusecase

import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavouriteTranslationsUseCaseImpl @Inject constructor(
    private val translationRepository: TranslationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): GetFavouriteTranslationsUseCase {

    override operator fun invoke(): Flow<List<WordTranslation>> {
        return translationRepository
            .getFavourites()
            .flowOn(dispatcher)
    }

}