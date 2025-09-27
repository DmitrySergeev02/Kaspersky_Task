package com.dmitrysergeev.translateapp.utils

object InputValidator {

    private val checkRegex = Regex("^[А-Яа-яЁё-]+$")

    fun isCorrect(text: String): Boolean = checkRegex.matches(text)
}