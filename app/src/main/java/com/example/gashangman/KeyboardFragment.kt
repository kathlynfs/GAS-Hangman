package com.example.gashangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.gashangman.databinding.FragmentKeyboardBinding

class KeyboardFragment : Fragment() {
    private var _binding: FragmentKeyboardBinding? = null
    private var keyboardPressed: BooleanArray = BooleanArray(26)
    private val letters = ('a'..'z').toList()
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
            FragmentKeyboardBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBooleanArray("keyboard", keyboardPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: SharedViewModel by activityViewModels()
        binding.apply {
            letters.forEachIndexed { index, letter ->
                val buttonId = resources.getIdentifier(letter.toString(), "id", requireActivity().packageName)
                val button = view.findViewById<Button>(buttonId)

                button.setOnClickListener {
                    it.isEnabled = false
                    keyboardPressed[index] = true
                    viewModel.setChar(button.text[0])
                }
            }
        }
        if (savedInstanceState != null) {
            keyboardPressed = savedInstanceState.getBooleanArray("keyboard") ?: BooleanArray(26)
            Log.d("TAG",keyboardPressed.contentToString())
            val buttons = arrayOf(
                binding.a, binding.b, binding.c, binding.d, binding.e, binding.f, binding.g,
                binding.h, binding.i, binding.j, binding.k, binding.l, binding.m, binding.n,
                binding.o, binding.p, binding.q, binding.r, binding.s, binding.t, binding.u,
                binding.v, binding.w, binding.x, binding.y, binding.z
            )

            buttons.forEachIndexed { index, button ->
                button.isEnabled = !keyboardPressed[index]
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}