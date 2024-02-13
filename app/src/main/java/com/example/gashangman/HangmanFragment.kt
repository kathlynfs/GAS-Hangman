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

class HangmanFragment : Fragment() {
    private var _binding: FragmentHangmanBinding? = null
    private var keyboardPressed: BooleanArray = BooleanArray(26)
    private var lives: Int = 6
    private var hintCount = 0
    //LOOK TO MAKE THIS NOT HARD-CODED
    private var word: String = "ANDROID"
    private var wordArr: CharArray = word.toCharArray()
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", R.string.word_to_guess.toString())
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
            if(hintCount == 3)
            {
                var vowels = "AEIOU"
                for(v in vowels)
                {
                    keyboardPressed[v - 'A'] = true
                }

                printWordAndLives()
            }
        })

        viewModel.getChar().observe(viewLifecycleOwner, Observer<Char> { input ->
            if (!keyboardPressed[input - 'A'] and !word.contains(input)) {
                lives -= 1
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
                    text.toString() + '_'
                }
                text = text.toString() + " "
            }
        }
    }
}