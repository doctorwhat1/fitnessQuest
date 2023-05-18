package com.example.fitnessquest.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.fitnessquest.R
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

        binding.cvStrolls.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_strollsFragment)
        }
        binding.cvFood.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_foodFragment)
        }
        binding.cvActivity.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_activityFragment)
        }
        binding.cvWater.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_waterFragment)
        }
        binding.cvSleep.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_sleepFragment)
        }
        binding.cvWeight.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_weightFragment)
        }

        binding.btnInputWeight.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_weightFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}