package com.example.fitnessquest.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessquest.databinding.FragmentCatBinding
import com.google.android.material.snackbar.Snackbar


class CatFragment : Fragment() {
    // view binding
    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding!!

    // view model
    lateinit var viewModel: CatViewModel
    lateinit var viewModelFactory: CatViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModelFactory = CatViewModelFactory(resources)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CatViewModel::class.java)

        binding.catViewModel = viewModel // set the data binding variable
        binding.lifecycleOwner = viewLifecycleOwner // lets the layout respond to live data updates

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}