package com.example.fitnessquest.home.cards

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitnessquest.*
import com.example.fitnessquest.databinding.FragmentSleepBinding


class SleepFragment : Fragment() {

    // view binding
    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnEnterSleepTime.setOnClickListener {
            val fallingAsleepTime = binding.etFallingAsleepTime.text.toString()
            val wakeupTime = binding.etWakeupTime.text.toString()

            val sharedPref = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val editor =  sharedPref.edit()

            val timeTemplate = resources.getString(R.string.cat_time_template)
            if (sharedPref.getString(FALLING_ASLEEP_TIME, timeTemplate) == timeTemplate
                || sharedPref.getString(WAKEUP_TIME, timeTemplate) == timeTemplate) {
                editor.putInt(CURRENT_HP, sharedPref.getInt(CURRENT_HP, 0) + 25)
            }

            editor.putString(FALLING_ASLEEP_TIME, fallingAsleepTime)
            editor.putString(WAKEUP_TIME, wakeupTime)

            editor.apply()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}