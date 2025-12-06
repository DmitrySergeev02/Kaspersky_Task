package com.dmitrysergeev.data.translation.sources.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApi {

    @GET("translate_a/t?client=dict-chrome-ex")
    suspend fun getTranslate(
        @Query("sl") originalLanguage: String,
        @Query("tl") desiredLanguage: String,
        @Query("q") textToTranslate: String,
    ): ResponseBody
}