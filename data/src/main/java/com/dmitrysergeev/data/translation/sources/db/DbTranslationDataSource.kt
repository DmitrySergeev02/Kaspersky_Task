package com.dmitrysergeev.data.translation.sources.db

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.data.history.mapper.WordTranslationDataEntityMapper
import com.dmitrysergeev.data.translation.sources.MutableTranslationDataSource
import javax.inject.Inject

class DbTranslationDataSource @Inject constructor(
    private val translationDao: TranslationDao,
    private val mapper: WordTranslationDataEntityMapper
): MutableTranslationDataSource {
    override suspend fun addToHistory(wordTranslationDataEntity: WordTranslationDataEntity) {
        translationDao.addToHistory(mapper.toHistoryDbEntity(wordTranslationDataEntity))
    }

    override suspend fun getTranslation(
        fromLanguage: String,
        toLanguage: String,
        input: String
    ): String = translationDao.getTranslation(fromLanguage, toLanguage, input)
}