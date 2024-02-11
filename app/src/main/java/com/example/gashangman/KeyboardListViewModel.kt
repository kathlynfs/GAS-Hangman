package com.example.gashangman

import androidx.lifecycle.ViewModel

class KeyboardListViewModel : ViewModel() {
    val keys = mutableListOf<Key>()
    init {
        for (c in 'a'..'z') {
            val key = Key(
                letter = c,
                pressed = false
            )
            keys += key
        }
    }
}