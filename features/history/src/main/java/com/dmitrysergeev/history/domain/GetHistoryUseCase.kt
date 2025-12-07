package com.dmitrysergeev.history.domain

import com.dmitrysergeev.core.di.IoDispatcher
import com.dmitrysergeev.history.domain.entities.HistoryTranslation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
){

    operator fun invoke(): Flow<List<HistoryTranslation>> {
        return historyRepository
            .getAllHistory()
            .flowOn(dispatcher)
    }

}