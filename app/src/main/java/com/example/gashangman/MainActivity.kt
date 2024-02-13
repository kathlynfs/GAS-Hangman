package com.example.gashangman

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

// define GuessWord class
data class GuessWord(val word: String, val clue: String)


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle orientation
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)
        } else {
            setContentView(R.layout.activity_main)
        }

        // implement communicator function
        val keyboardFragment = KeyboardFragment()
        supportFragmentManager.commit {
            replace(R.id.keyboard_fragment_container, keyboardFragment)
        }

    }



}