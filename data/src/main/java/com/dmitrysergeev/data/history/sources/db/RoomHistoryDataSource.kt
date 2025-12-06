package com.dmitrysergeev.data.history.sources.db

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.data.history.mapper.WordTranslationMapper
import com.dmitrysergeev.data.history.sources.HistoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomHistoryDataSource @Inject constructor(
    private val historyDao: HistoryDao,
    private val mapper: WordTranslationMapper
): HistoryDataSource{
    override fun getAllItemsFromHistory(): Flow<List<WordTranslationDataEntity>> = historyDao.getHistory()
        .map { list-> list.map { mapper.toWorldTranslationDataEntity(it) } }


    override suspend fun deleteFromHistory(wordTranslationDataEntity: WordTranslationDataEntity) {
        historyDao.deleteHistoryItem(mapper.toHistoryDbEntity(wordTranslationDataEntity))
    }

}