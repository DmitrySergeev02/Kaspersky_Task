package com.dmitrysergeev.translateapp.domain.usecases

import com.dmitrysergeev.translateapp.di.IoDispatcher
import com.dmitrysergeev.translateapp.domain.history.HistoryRepository
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHistoryItemUseCase @Inject constructor(
    private val translationRepository: HistoryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
){

    suspend operator fun invoke(worldTranslation: WordTranslation) = withContext(dispatcher){
        translationRepository.deleteFromHistory(worldTranslation)
    }

}