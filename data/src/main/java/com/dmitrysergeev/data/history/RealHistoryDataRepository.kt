package com.dmitrysergeev.data.history

import com.dmitrysergeev.data.HistoryDataRepository
import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.data.history.sources.HistoryDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealHistoryDataRepository @Inject constructor(
    private val dbHistoryDataSource: HistoryDataSource
) : HistoryDataRepository {
    override fun getAllHistory(): Flow<List<WordTranslationDataEntity>> =
        dbHistoryDataSource.getAllItemsFromHistory()

    override suspend fun deleteFromHistory(wordTranslationDataEntity: WordTranslationDataEntity) =
        dbHistoryDataSource.deleteFromHistory(wordTranslationDataEntity)

}