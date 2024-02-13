package com.example.gashangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.gashangman.databinding.FragmentHintBinding

class HintFragment : Fragment() {
    private var _binding: FragmentHintBinding? = null
    private lateinit var word: String
    private lateinit var wordArr: CharArray
    private var hints: Int = 3

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = getString(R.string.word_to_guess)
        wordArr = word.toCharArray()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentHintBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("hints", hints)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: SharedViewModel by activityViewModels()
        if (savedInstanceState != null) {
            hints = savedInstanceState.getInt("hints") ?: 3
        }
        if (hints <= 2) {
            binding.hint.text = getString(R.string.hint)
        }
        binding.apply {
            hintButton.setOnClickListener{
                hints--
                Log.d("TAG", hints.toString())
                if (hints <= 2) {
                    binding.hint.text = getString(R.string.hint)
                }
                viewModel.setHints(hints)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}