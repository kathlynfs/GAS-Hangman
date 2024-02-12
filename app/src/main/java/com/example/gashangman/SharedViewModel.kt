package com.example.gashangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val char = MutableLiveData<Char>()

    fun setChar(input: Char) {
        char.value = input
    }
    fun getChar() : LiveData<Char> {
        return char
    }
}