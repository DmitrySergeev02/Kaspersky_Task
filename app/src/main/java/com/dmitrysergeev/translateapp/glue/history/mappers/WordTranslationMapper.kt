package com.dmitrysergeev.translateapp.glue.history.mappers

import com.dmitrysergeev.data.history.entities.WordTranslationDataEntity
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation
import javax.inject.Inject

class WordTranslationMapper @Inject constructor() {
    fun toWordTranslation(wordTranslationDataEntity: WordTranslationDataEntity): WordTranslation{
        return WordTranslation(
            id = wordTranslationDataEntity.id,
            fromLanguage = wordTranslationDataEntity.fromLanguage,
            toLanguage = wordTranslationDataEntity.toLanguage,
            input = wordTranslationDataEntity.input,
            output = wordTranslationDataEntity.output
        )
    }

    fun toWordTranslationDataEntity(wordTranslation: WordTranslation): WordTranslationDataEntity{
        return WordTranslationDataEntity(
            id = wordTranslation.id,
            fromLanguage = wordTranslation.fromLanguage,
            toLanguage = wordTranslation.toLanguage,
            input = wordTranslation.input,
            output = wordTranslation.output
        )
    }
}