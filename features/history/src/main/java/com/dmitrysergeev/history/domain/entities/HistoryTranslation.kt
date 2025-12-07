package com.dmitrysergeev.history.domain.entities

data class HistoryTranslation(
    val id: Long,
    val fromLanguage: String,
    val toLanguage: String,
    val input: String,
    val output: String
)