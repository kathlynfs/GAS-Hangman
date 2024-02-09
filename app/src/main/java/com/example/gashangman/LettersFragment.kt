package com.example.gashangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gashangman.databinding.FragmentLettersBinding

class LettersFragment : Fragment()
{
    private lateinit var binding: FragmentLettersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentLettersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}