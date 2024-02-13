package com.example.gashangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.gashangman.databinding.FragmentHintBinding

class HintFragment : Fragment()
{
    private var _binding: FragmentHintBinding? = null
    private var hintCount = 0
    private var lives = 6

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
            FragmentHintBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("hintCount", hintCount)
        outState.putInt("lives", lives)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: SharedViewModel by activityViewModels()

        viewModel.setLives(lives)
        viewModel.setHintCount(hintCount)

        binding.hintButton.setOnClickListener {
            lives = viewModel.getLives().value!!
            hintCount = viewModel.getHintCount().value!!

            if(hintCount == 0)
            {
                binding.hint.setText(R.string.hint_android)
                hintCount +=1
                viewModel.setIsReturningState(false)
                viewModel.setHintCount(hintCount)
            }
            else if(hintCount == 1 && lives > 1)
            {
                hintCount +=1
                lives -=1
                viewModel.setIsReturningState(false)
                viewModel.setHintCount(hintCount)
                viewModel.setLives(lives)

                Toast.makeText(
                    requireContext(),
                    R.string.second_hint,
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(hintCount == 2 && lives > 1)
            {
                hintCount +=1
                lives -=1
                viewModel.setIsReturningState(false)
                viewModel.setHintCount(hintCount)
                viewModel.setLives(lives)

                Toast.makeText(
                    requireContext(),
                    R.string.third_hint,
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(hintCount > 2)
            {
                Toast.makeText(
                    requireContext(),
                    R.string.out_of_hints,
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(lives <= 1)
            {
                Toast.makeText(
                    requireContext(),
                    R.string.too_few_lives,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.getLives().observe(viewLifecycleOwner, Observer<Int> { input ->
            lives = input
        })

        if (savedInstanceState != null)
        {
            viewModel.setIsReturningState(true)
            hintCount = savedInstanceState.getInt("hintCount")
            lives = savedInstanceState.getInt("lives")

            if(hintCount >= 1)
            {
                binding.hint.setText(R.string.hint_android)
            }

            viewModel.setHintCount(hintCount)

            viewModel.setLives(lives)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}