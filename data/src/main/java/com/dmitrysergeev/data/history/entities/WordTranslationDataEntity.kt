package com.dmitrysergeev.data.history.entities

data class WordTranslationDataEntity(
    val id: Long,
    val fromLanguage: String,
    val toLanguage: String,
    val input: String,
    val output: String
)
