package com.dmitrysergeev.translateapp.data.translation.network

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.translation.TranslationDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitTranslationDataSource @Inject constructor(
    private val skyEngApi: SkyEngApi
): TranslationDataSource {
    override fun getTranslations(query: String): Flow<List<WordTranslation>> {
        return flow {
            emit(
                skyEngApi.getMeanings(query)
                    .filter { it.meanings.isNotEmpty() }
                    .map {
                        item-> WordTranslation(id = item.id, originalWord = query, translation = item.meanings[0].translation.text)
                    }
            )
        }
    }
}