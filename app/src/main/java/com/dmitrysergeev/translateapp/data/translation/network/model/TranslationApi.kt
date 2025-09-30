package com.dmitrysergeev.translateapp.data.translation.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranslationApi(
    val text: String
)
