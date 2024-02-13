package com.example.gashangman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val char = MutableLiveData<Char>()

    private var lives = MutableLiveData<Int>()

    private var hintCount = MutableLiveData<Int>()

    private var isReturningState = false

    fun setChar(input: Char) {
        char.value = input
    }
    fun getChar() : LiveData<Char> {
        return char
    }

    fun setLives(input: Int)
    {
        lives.value = input
    }
    fun getLives(): LiveData<Int>
    {
        return lives
    }

    fun setHintCount(input: Int)
    {
        hintCount.value = input
    }

    fun getHintCount(): LiveData<Int>
    {
        return hintCount
    }

    fun setIsReturningState(input: Boolean)
    {
        isReturningState = input
    }

    fun getIsReturningState() : Boolean
    {
        return isReturningState
    }
}