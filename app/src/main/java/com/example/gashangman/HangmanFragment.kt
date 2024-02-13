package com.example.gashangman

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.gashangman.databinding.FragmentHangmanBinding
import kotlin.random.Random

class HangmanFragment : Fragment() {
    private var _binding: FragmentHangmanBinding? = null
    private var keyboardPressed: BooleanArray = BooleanArray(26)
    private var lives: Int = 6
    private var hints: Int = 3
    private var seed: Int = 123
    private var toastMade: Boolean = false
    private val random = Random(seed)
    private lateinit var word: String
    private lateinit var wordArr: CharArray
    private var currentIndex: Int = 0

    val wordBank: List<Int>
        get() = listOf(
            R.string.guess_android,
            R.string.guess_camera,
            R.string.guess_fragment,
            R.string.guess_activity,
            R.string.guess_you
        )
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentHangmanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("keyboard", keyboardPressed)
        outState.putInt("lives", lives)
        outState.putBoolean("toastMade", toastMade)
        outState.putInt("currentIndex", currentIndex)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            keyboardPressed = savedInstanceState.getBooleanArray("keyboard") ?: BooleanArray(26)
            lives = savedInstanceState.getInt("lives") ?: 6
            toastMade = savedInstanceState.getBoolean("toastMade") ?: false
            currentIndex = savedInstanceState.getInt("currentIndex") ?: 0
        }
        word = getString(wordBank[currentIndex])
        wordArr = word.toCharArray()

        binding.newGameButton.setOnClickListener {
            resetGame()
        }
        printWordAndLives()
        binding.imageView.apply {
            setImageResource(R.drawable.hangman_state_6_lives)
        }
        val model: SharedViewModel by activityViewModels()

        model.getHints().observe(viewLifecycleOwner, Observer<Int> { input ->
            hints = input
            if (hints != 2) {
                if (lives > 1) {
                    if (hints == 1) {
                        var i = 0
                        for (c in 'A'..'Z') {
                            if (!word.contains(c) && random.nextBoolean()) {
                                keyboardPressed[c - 'A'] = true
                                model.setHangmanToKeyboard(c)
                                i++
                            }
                            if (i >= 13) {
                                break
                            }
                        }
                        lives--
                    } else if (hints == 0) {
                        var i = 0
                        for (c in 'A'..'Z') {
                            if (!word.contains(c) && random.nextBoolean()) {
                                keyboardPressed[c - 'A'] = true
                                model.setHangmanToKeyboard(c)
                                i++
                            }
                            if (i >= 13) {
                                break
                            }
                        }
                        lives--
                    }
                    printWordAndLives()
                } else {
                    if (!toastMade) {
                        Toast.makeText(
                            requireContext(),
                            R.string.too_few_lives,
                            Toast.LENGTH_LONG
                        ).show()
                        toastMade = true
                    }
                }
            }


        })

        model.getChar().observe(viewLifecycleOwner, Observer<Char> { input ->
            if (input != 'a') {
                if (!keyboardPressed[input - 'A'] and !word.contains(input)) {
                    lives -= 1
                }

                printWordAndLives()

                keyboardPressed[input - 'A'] = true

                printWordAndLives()
                checkGameState()
            }

        })


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
                    text.toString() + '_'
                }
                text = text.toString() + " "
            }
        }
        Log.d("TAG", lives.toString())
        binding.imageView.apply{
            if (lives == 6) {
                setImageResource(R.drawable.hangman_state_6_lives)
            } else if (lives == 5) {
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
    }

    private fun checkGameState(){
        var success = true
        for (c in wordArr) {
            if (!keyboardPressed[c - 'A']) {
                success = false
                break
            }
        }
        if (success) {
            gameOver(true) // Player successfully guessed all letters
        } else if (lives == 0) {
            gameOver(false) // Player lost all lives without guessing the word
        }
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
        val viewModel: SharedViewModel by activityViewModels()
        // Reset lives
        lives = 6
        Log.d("TAGGGGG", lives.toString())

        viewModel.setHintCount(3)

        keyboardPressed.fill(false)

        // Update the word
        currentIndex = (currentIndex + 1) % wordBank.size // Increment currentIndex to cycle through words
        word = getString(wordBank[currentIndex])
        wordArr = word.toCharArray()
        viewModel.setCurrentIndex(currentIndex)

        viewModel.setHangmanToKeyboard('a')

        // Reset the UI to reflect the changes
        printWordAndLives()
    }

}