package com.dmitrysergeev.translateapp.data.translation.api

import com.dmitrysergeev.translateapp.data.translation.api.model.WordTranslationApi
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiTranslationRepository @Inject constructor(
    private val skyEngApi: SkyEngApi
): TranslationRepository {
    override fun getTranslations(query: String): Flow<List<WordTranslation>> {
        return flow {
            emit(
                skyEngApi.getMeanings(query).map {
                    item-> WordTranslation(id = item.id, originalWord = query, translation = item.text)
                }
            )
        }
    }
}