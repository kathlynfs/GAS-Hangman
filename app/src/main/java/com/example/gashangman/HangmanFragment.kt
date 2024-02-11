package com.example.gashangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.gashangman.databinding.FragmentHangmanBinding

class HangmanFragment : Fragment() {
    private var _binding: FragmentHangmanBinding? = null
    private var keyboardPressed: BooleanArray = BooleanArray(26)
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: SharedViewModel by activityViewModels()
        model.getChar().observe(viewLifecycleOwner, Observer<Char> { input ->
            binding.word.apply {
                keyboardPressed[input - 'A'] = true
                text = ""
                for (c in wordArr) {
                    Log.d("TAG", c.toString())
                    if (keyboardPressed[c - 'A']) {
                        text = text.toString() + c
                    } else {
                        text = text.toString() + '_'
                    }
                    text = text.toString() + " "
                }

            }
        })

        if (savedInstanceState != null) {
            keyboardPressed = savedInstanceState.getBooleanArray("keyboard") ?: BooleanArray(26)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}