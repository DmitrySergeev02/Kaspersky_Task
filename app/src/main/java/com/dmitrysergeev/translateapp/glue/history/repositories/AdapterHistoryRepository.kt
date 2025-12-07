package com.dmitrysergeev.translateapp.glue.history.repositories

import com.dmitrysergeev.data.HistoryDataRepository
import com.dmitrysergeev.history.domain.HistoryRepository
import com.dmitrysergeev.history.domain.entities.HistoryTranslation
import com.dmitrysergeev.translateapp.glue.history.mappers.HistoryTranslationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterHistoryRepository @Inject constructor(
    private val historyDataRepository: HistoryDataRepository,
    private val mapper: HistoryTranslationMapper
): HistoryRepository {
    override fun getAllHistory(): Flow<List<HistoryTranslation>>
        = historyDataRepository.getAllHistory()
            .map { list-> list.map { mapper.toWordTranslation(it) } }

    override suspend fun deleteFromHistory(historyTranslation: HistoryTranslation)
        = historyDataRepository.deleteFromHistory(mapper.toWordTranslationDataEntity(historyTranslation))
}