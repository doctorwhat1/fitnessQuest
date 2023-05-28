package com.example.fitnessquest.home

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.fitnessquest.APP_PREFERENCES
import com.example.fitnessquest.CURRENT_HP
import com.example.fitnessquest.R
import com.example.fitnessquest.databinding.FragmentCatBinding


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

        // Adding a context of SENSOR_SERVICE as Sensor Manager
        val sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        viewModelFactory = CatViewModelFactory(
            sensorManager,
            resources,
            requireContext()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CatViewModel::class.java)

        binding.catViewModel = viewModel // set the data binding variable
        binding.lifecycleOwner = viewLifecycleOwner // lets the layout respond to live data updates

        // cat HP
        viewModel.setHP()


        viewModel.setTotalCalories()
        viewModel.setCurrentActivity()
        viewModel.setSleepTime()
        viewModel.setCurrentWeight()

        // rewards
        viewModel.setIsBreakfastEntered()
        viewModel.setIsLunchEntered()
        viewModel.setIsDinnerEntered()


        // set cards on click
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

        // set cards buttons on click
        binding.btnInputFood.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_foodFragment)
        }
        binding.btnInputActivity.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_activityFragment)
        }
        binding.btnChangeSleepTime.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_sleepFragment)
        }
        binding.btnInputWeight.setOnClickListener {
            view.findNavController().navigate(R.id.action_catFragment_to_weightFragment)
        }

        binding.btnPerformWarmup.setOnClickListener { viewModel.increaseHP() }

        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResumeStepCounter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}