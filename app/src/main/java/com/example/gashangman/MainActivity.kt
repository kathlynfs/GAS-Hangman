package com.example.gashangman

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// define GuessWord class
data class GuessWord(val stringResourceId: Int, val word: CharArray?)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape)
        } else {
            setContentView(R.layout.activity_main)
        }

        // Find and instantiate KeyboardFragment
        val keyboardFragment = supportFragmentManager.findFragmentById(R.id.keyboardFragment) as KeyboardFragment
        keyboardFragment.setInteractionListener(this)
    }

    override fun resetKeyboard() {
        val keyboardFragment = supportFragmentManager.findFragmentById(R.id.keyboardFragment) as? KeyboardFragment
        keyboardFragment?.resetKeyboard()
    }

}