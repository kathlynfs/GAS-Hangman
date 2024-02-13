package com.example.gashangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.gashangman.databinding.FragmentHangmanBinding
import android.app.AlertDialog
import android.widget.Button

class HangmanFragment : Fragment() {
    private var _binding: FragmentHangmanBinding? = null
    private var keyboardPressed: BooleanArray = BooleanArray(26)
    private var lives: Int = 6
    private var hintCount = 0
    private lateinit var word: String
    private lateinit var wordArr: CharArray
    private lateinit var newGameButton: Button

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    val wordBank: List<Int>
        get() = listOf(
            R.string.guess_android,
            R.string.guess_camera,
            R.string.guess_fragment,
            R.string.guess_activity,
            R.string.guess_you
        )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun updateWord() {
        // Update the word with the next word from the word bank
        word = getString(wordBank[currentIndex])
        wordArr = word.toCharArray()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentHangmanBinding.inflate(layoutInflater, container, false)
        word = getString(R.string.guess_android)
        wordArr = word.toCharArray()

        // Initialize the newGameButton
        binding.newGameButton.setOnClickListener {
            // Increment currentIndex to cycle through words
            currentIndex = (currentIndex + 1) % wordBank.size
            // Update the word
            updateWord()
            // Reset the game state
            resetGame()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("keyboard", keyboardPressed)
        outState.putInt("lives", lives)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG", R.string.guess_android.toString())


        printWordAndLives()
        binding.imageView.apply {
            setImageResource(R.drawable.hangman_state_6_lives)
        }
        val viewModel: SharedViewModel by activityViewModels()

        viewModel.getLives().observe(viewLifecycleOwner, Observer<Int> {input ->
            lives = input
            binding.imageView.apply{
                if (lives == 5) {
                    setImageResource(R.drawable.hangman_state_5_lives)
                } else if (lives == 4) {
                    setImageResource(R.drawable.hangman_state_4_lives)
                } else if (lives == 3) {
                    setImageResource(R.drawable.hangman_state_3_lives)
                } else if (lives == 2) {
                    setImageResource(R.drawable.hangman_state_2_lives)
                } else if (lives == 1) {
                    setImageResource(R.drawable.hangman_state_1_lives)
                } else if (lives <= 0) {
                    setImageResource(R.drawable.hangman_state_0_lives)
                }
            }
        })

        viewModel.getHintCount().observe(viewLifecycleOwner, Observer<Int> {input ->
            hintCount = input
            if(!viewModel.getIsReturningState()) {
                if (hintCount == 3) {
                    var vowels = "AEIOU"
                    for (v in vowels) {
                        keyboardPressed[v - 'A'] = true
                    }

                    printWordAndLives()
                }
            }
        })

        viewModel.getChar().observe(viewLifecycleOwner, Observer<Char> { input ->
            if (!keyboardPressed[input - 'A'] and !word.contains(input)) {
                lives -= 1
                checkGameState()
                viewModel.setLives(lives)
            }

            keyboardPressed[input - 'A'] = true

            printWordAndLives()
        })

        if (savedInstanceState != null) {
            keyboardPressed = savedInstanceState.getBooleanArray("keyboard") ?: BooleanArray(26)
            lives = savedInstanceState.getInt("lives") ?: 6
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun printWordAndLives() {
        binding.word.apply {
            text = ""
            for (c in wordArr) {
                text = if (keyboardPressed[c - 'A']) {
                    text.toString() + c
                } else {
                    text.toString() + getString(R.string.dash)
                }
                text = text.toString() + getString(R.string.space)
            }
        }
    }

    private fun checkGameState(){
        if (lives == 0) {
            gameOver(false)
        }
        // implement success state
    }

    private fun gameOver(success: Boolean) {
        // assign the correct message depending on win/lose
        val message = if (success) {
            getString(R.string.success_message)
        } else {
            getString(R.string.failed_message)
        }

        // setup the dialog
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                resetGame()
                dialog.dismiss()
            }
            .setCancelable(false) // Prevent dismissing dialog when clicking outside or back button
            .show()
    }

    private fun resetGame() {
        // Reset lives
        lives = 6

        // Reset hint count
        hintCount = 0

        // Reset keyboard
        keyboardPressed = BooleanArray(26)

        // Reset hangman view
        binding.imageView.setImageResource(R.drawable.hangman_state_6_lives)

        // Reset the UI to reflect the changes
        printWordAndLives()
    }


}