package com.dmitrysergeev.translateapp.glue.history.mappers

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.history.domain.entities.HistoryTranslation
import javax.inject.Inject

class HistoryTranslationMapper @Inject constructor() {
    fun toWordTranslation(wordTranslationDataEntity: WordTranslationDataEntity): HistoryTranslation {
        return HistoryTranslation(
            id = wordTranslationDataEntity.id,
            fromLanguage = wordTranslationDataEntity.fromLanguage,
            toLanguage = wordTranslationDataEntity.toLanguage,
            input = wordTranslationDataEntity.input,
            output = wordTranslationDataEntity.output
        )
    }

    fun toWordTranslationDataEntity(wordTranslation: HistoryTranslation): WordTranslationDataEntity{
        return WordTranslationDataEntity(
            id = wordTranslation.id,
            fromLanguage = wordTranslation.fromLanguage,
            toLanguage = wordTranslation.toLanguage,
            input = wordTranslation.input,
            output = wordTranslation.output
        )
    }
}