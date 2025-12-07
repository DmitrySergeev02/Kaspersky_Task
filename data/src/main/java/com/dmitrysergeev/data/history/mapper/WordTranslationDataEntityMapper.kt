package com.dmitrysergeev.data.history.mapper

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.data.history.sources.db.entities.HistoryDbEntity
import javax.inject.Inject

class WordTranslationDataEntityMapper @Inject constructor() {
    fun toHistoryDbEntity(wordTranslationDataEntity: WordTranslationDataEntity): HistoryDbEntity{
        return HistoryDbEntity(
            id = wordTranslationDataEntity.id,
            fromLanguage = wordTranslationDataEntity.fromLanguage,
            toLanguage = wordTranslationDataEntity.toLanguage,
            input = wordTranslationDataEntity.input,
            output = wordTranslationDataEntity.output
        )
    }

    fun toWorldTranslationDataEntity(historyDbEntity: HistoryDbEntity): WordTranslationDataEntity{
        return WordTranslationDataEntity(
            id = historyDbEntity.id,
            fromLanguage = historyDbEntity.fromLanguage,
            toLanguage = historyDbEntity.toLanguage,
            input = historyDbEntity.input,
            output = historyDbEntity.output
        )
    }
}