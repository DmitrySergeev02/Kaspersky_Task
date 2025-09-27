package com.dmitrysergeev.translateapp.data.translation.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WordApi(
    val id: Long,
    val text: String,
)