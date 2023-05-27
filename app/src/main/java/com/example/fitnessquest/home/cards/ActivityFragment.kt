package com.example.fitnessquest.home.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.fitnessquest.R
import com.example.fitnessquest.databinding.FragmentActivityBinding
import com.example.fitnessquest.databinding.FragmentCatBinding


class ActivityFragment : Fragment() {
    // view binding
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}