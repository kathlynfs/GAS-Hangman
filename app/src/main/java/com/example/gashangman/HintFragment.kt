package com.example.gashangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gashangman.databinding.FragmentHintBinding

class HintFragment : Fragment()
{
    private lateinit var binding: FragmentHintBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentHintBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}