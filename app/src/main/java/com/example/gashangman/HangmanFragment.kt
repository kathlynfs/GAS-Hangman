package com.example.gashangman

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
        word = getString(R.string.guess_android)
        wordArr = word.toCharArray()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("keyboard", keyboardPressed)
        outState.putInt("lives", lives)
        outState.putBoolean("toastMade", toastMade)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG", R.string.guess_android.toString())
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
            if (!keyboardPressed[input - 'A'] and !word.contains(input)) {
                lives -= 1
            }

            printWordAndLives()

            keyboardPressed[input - 'A'] = true

            printWordAndLives()
        })

        if (savedInstanceState != null) {
            keyboardPressed = savedInstanceState.getBooleanArray("keyboard") ?: BooleanArray(26)
            lives = savedInstanceState.getInt("lives") ?: 6
            toastMade = savedInstanceState.getBoolean("toastMade") ?: false
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
                    text.toString() + '_'
                }
                text = text.toString() + " "
            }
        }
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
    }


}