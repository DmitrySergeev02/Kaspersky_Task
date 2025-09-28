package com.dmitrysergeev.translateapp.data.translation.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WordTranslationApi(
    val id: Long,
    val text: String,
)