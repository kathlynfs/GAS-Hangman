package com.example.gashangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import com.example.gashangman.databinding.FragmentHintBinding

class HintFragment : Fragment()
{
    private var _binding: FragmentHintBinding? = null
    private var hintCount = 0

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
        // outState.putInt("lives", lives)
        // save number of hints given
        //save current state of letters left on keyboard
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: SharedViewModel by activityViewModels()

        binding.hintButton.setOnClickListener {
            val lives = viewModel.getLives()

            if(hintCount == 0 && lives > 1)
            {

            }
            else if(hintCount == 1 && lives > 1)
            {

            }
            else if(hintCount == 2 && lives > 1)
            {

            }
            else
            {
                // create toast to say hint cannot be requested
                /*
                Toast.makeText(
                    this,
                    "string",
                    Toast.LENGTH_SHORT
                ).show()
                */

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}