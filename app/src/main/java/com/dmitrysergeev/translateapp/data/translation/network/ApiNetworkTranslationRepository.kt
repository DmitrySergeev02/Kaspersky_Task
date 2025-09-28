package com.dmitrysergeev.translateapp.data.translation.network

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiNetworkTranslationRepository @Inject constructor(
    private val skyEngApi: SkyEngApi
): NetworkTranslationRepository {
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