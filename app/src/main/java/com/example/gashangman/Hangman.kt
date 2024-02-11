package com.example.gashangman

data class Hangman (
    val lives: Int,
    val word: String,
    val guessed: BooleanArray,
    val isSolved: Boolean
)