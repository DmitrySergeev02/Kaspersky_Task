package com.dmitrysergeev.translateapp.domain.translation.entities

data class WordTranslation(
    val id: Long,
    val fromLanguage: String,
    val toLanguage: String,
    val input: String,
    val output: String
)