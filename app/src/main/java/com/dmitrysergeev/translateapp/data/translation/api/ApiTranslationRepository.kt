package com.dmitrysergeev.translateapp.data.translation.api

import com.dmitrysergeev.translateapp.data.translation.api.model.WordApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiTranslationRepository @Inject constructor(
    private val skyEngApi: SkyEngApi
): TranslationRepository {
    override fun getTranslations(query: String): Flow<List<WordApi>> {
        return flow {
            emit(skyEngApi.getMeanings(query))
        }
    }
}