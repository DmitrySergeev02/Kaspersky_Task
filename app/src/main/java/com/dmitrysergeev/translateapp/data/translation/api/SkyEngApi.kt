package com.dmitrysergeev.translateapp.data.translation.api

import com.dmitrysergeev.translateapp.data.translation.api.model.WordTranslationApi
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyEngApi {

    @GET("words/search")
    suspend fun getMeanings(
        @Query("search") search: String
    ): List<WordTranslationApi>

}