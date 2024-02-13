package com.example.gashangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val char = MutableLiveData<Char>()
    private val lives = MutableLiveData<Int>()
    private var currentIndex = MutableLiveData<Int>()

    fun getCurrentIndex(): MutableLiveData<Int> {
        return currentIndex
    }
    fun setCurrentIndex(input: Int) {
        currentIndex.value = input
    }
    fun setHints(input: Int) {
        lives.value = input
    }

    fun getHints() : LiveData<Int> {
        return lives
    }

    fun setChar(input: Char) {
        char.value = input
    }
    fun getChar() : LiveData<Char> {
        return char
    }
    fun setHangmanToKeyboard(input: Char) {
        char.value = input
    }
    fun getHangmanToKeyboard() : LiveData<Char> {
        return char
    }
}