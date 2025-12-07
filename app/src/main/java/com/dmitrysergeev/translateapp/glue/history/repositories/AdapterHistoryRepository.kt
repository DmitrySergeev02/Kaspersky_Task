package com.dmitrysergeev.translateapp.glue.history.repositories

import com.dmitrysergeev.data.HistoryDataRepository
import com.dmitrysergeev.translateapp.domain.history.HistoryRepository
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.glue.history.mappers.WordTranslationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterHistoryRepository @Inject constructor(
    private val historyDataRepository: HistoryDataRepository,
    private val mapper: WordTranslationMapper
): HistoryRepository {
    override fun getAllHistory(): Flow<List<WordTranslation>>
        = historyDataRepository.getAllHistory()
            .map { list-> list.map { mapper.toWordTranslation(it) } }

    override suspend fun deleteFromHistory(wordTranslation: WordTranslation)
        = historyDataRepository.deleteFromHistory(mapper.toWordTranslationDataEntity(wordTranslation))
}