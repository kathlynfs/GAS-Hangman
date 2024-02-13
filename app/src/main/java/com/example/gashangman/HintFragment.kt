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
import androidx.lifecycle.Observer

class HintFragment : Fragment() {
    private var _binding: FragmentHintBinding? = null
    private lateinit var word: String
    private lateinit var wordArr: CharArray
    private var hints: Int = 3
    private lateinit var currHint: String
    private var pastIndex = 0
    private var currentIndex = 0

    private lateinit var hintBank: List<Int>


    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = getString(R.string.guess_android)
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
        outState.putInt("currentIndex", currentIndex)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            hints = savedInstanceState.getInt("hints") ?: 3
            currentIndex = savedInstanceState.getInt("currentIndex") ?: 0
        }
        val viewModel: SharedViewModel by activityViewModels()

        hintBank = listOf(
            R.string.hint_android,
            R.string.hint_camera,
            R.string.hint_fragment,
            R.string.hint_activity,
            R.string.hint_you
        )
        currHint = getString(hintBank[currentIndex])

        viewModel.getCurrentIndex().observe(viewLifecycleOwner, Observer<Int> { input ->
            Log.d("TAG", input.toString())
            currentIndex = input
            currHint = getString(hintBank[currentIndex])
        })

        viewModel.getHintCount().observe(viewLifecycleOwner, Observer<Int> { input ->
            Log.d("TAG", "DAOUSODHAS")
            hints = input
            binding.hint.text = ""
        })


        if (hints <= 2) {
            binding.hint.text = currHint
        } else {
            binding.hint.text = ""
        }
        binding.apply {
            hintButton.setOnClickListener{
                hints--
                Log.d("TAG", hints.toString())
                if (hints <= 2) {
                    binding.hint.text = currHint
                }
                viewModel.setHints(hints)
            }
        }

    }

    private fun updateHint(index: Int) {
        Log.d("TAG", getString(hintBank[currentIndex]))
        currHint = getString(hintBank[currentIndex])
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}