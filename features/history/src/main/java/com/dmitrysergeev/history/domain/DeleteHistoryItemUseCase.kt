package com.dmitrysergeev.history.domain

import com.dmitrysergeev.core.di.IoDispatcher
import com.dmitrysergeev.history.domain.entities.HistoryTranslation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHistoryItemUseCase @Inject constructor(
    private val translationRepository: HistoryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
){

    suspend operator fun invoke(historyTranslation: HistoryTranslation) = withContext(dispatcher){
        translationRepository.deleteFromHistory(historyTranslation)
    }

}