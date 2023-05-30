package com.example.fitnessquest.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.fitnessquest.APP_PREFERENCES
import com.example.fitnessquest.CURRENT_HP
import com.example.fitnessquest.PREVIOUS_SAVED_DATE
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
        val sharedPreferences = requireContext()
            .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        viewModelFactory = CatViewModelFactory(
            sensorManager,
            resources,
            sharedPreferences
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CatViewModel::class.java)

        binding.catViewModel = viewModel // set the data binding variable
        binding.lifecycleOwner = viewLifecycleOwner // lets the layout respond to live data updates


        viewModel.checkDateForReloadData()

        viewModel.setRequiredTotalSteps()
        viewModel.setRequiredTotalCalories()

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
        viewModel.setIsSleepTimeEntered()


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

        binding.btnPerformWarmup.setOnClickListener {

            var builder = AlertDialog.Builder(activity)
            builder.setTitle("Упражнение")
            builder.setMessage("Выбранное упражнение необходимо выполнить в реальной жизни, в комфортном для вас темпе и соблюдая технику безопасности")
            builder.setPositiveButton("Выполнить", DialogInterface.OnClickListener {
                    dialog, id -> Toast.makeText(context,"Completed!", Toast.LENGTH_SHORT).show()
                dialog.cancel()
                viewModel.increaseHP()
            })

            builder.setNegativeButton("Отмена", DialogInterface.OnClickListener {
                    dialog, id ->
                dialog.cancel()

            })
            var alert = builder.create()

            val popupMenu: PopupMenu = PopupMenu(context,binding.btnPerformWarmup)
            popupMenu.menuInflater.inflate(R.menu.warmup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.pushupX5 -> {
                        //  Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                        //    .show()
                        alert = builder.create()
                        alert.show()

                    }
                    R.id.pushupX10 ->{
                        //   Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        alert = builder.create()
                        alert.show()
                    }

                    R.id.situpX5 -> {
                        //  Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        alert = builder.create()
                        alert.show()
                    }
                }
                true
            })
            popupMenu.show()


           // viewModel.increaseHP()
        }

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