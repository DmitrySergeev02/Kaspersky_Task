package com.dmitrysergeev.data.translation.sources.api

import android.util.Log
import com.dmitrysergeev.data.translation.sources.TranslationDataSource
import javax.inject.Inject

class ApiTranslationDataSource @Inject constructor(
    private val translateApi: TranslateApi
): TranslationDataSource {
    override suspend fun getTranslation(fromLanguage: String, toLanguage: String, input: String): String {
        val responseBody = translateApi.getTranslate(fromLanguage,toLanguage,input)
        val stringFromResponseBody = responseBody.charStream().readText()
        responseBody.close()
        Log.d("TAGTAG", "From api: $stringFromResponseBody")
        return stringFromResponseBody.substring(2, stringFromResponseBody.length - 2)
    }
}