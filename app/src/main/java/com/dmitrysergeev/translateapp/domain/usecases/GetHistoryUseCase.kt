package com.dmitrysergeev.translateapp.domain.usecases

import com.dmitrysergeev.translateapp.di.IoDispatcher
import com.dmitrysergeev.translateapp.domain.history.HistoryRepository
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
){

    operator fun invoke(): Flow<List<WordTranslation>> {
        return historyRepository
            .getAllHistory()
            .flowOn(dispatcher)
    }

}