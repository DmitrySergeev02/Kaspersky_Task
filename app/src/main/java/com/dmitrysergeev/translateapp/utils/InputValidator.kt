package com.dmitrysergeev.translateapp.utils

object InputValidator {

    private val checkRegex = Regex("^[A-Za-z-]+$")

    fun isCorrect(text: String): Boolean = checkRegex.matches(text)
}