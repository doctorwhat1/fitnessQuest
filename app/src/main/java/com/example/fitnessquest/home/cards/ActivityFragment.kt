package com.example.fitnessquest.home.cards

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitnessquest.*
import com.example.fitnessquest.databinding.FragmentActivityBinding


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

        binding.btnEnterActivityAf.setOnClickListener {
            val enteredActivityMins = binding.etEditActivityAf.text.toString().toInt()
            
            if (enteredActivityMins < 0 || enteredActivityMins > 200) {
                Toast.makeText(context, "Введите корректное число минут", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val editor =  sharedPref.edit()
            editor.putInt(CURRENT_ACTIVITY_MINS, sharedPref.getInt(CURRENT_ACTIVITY_MINS, 0) + enteredActivityMins)
            editor.apply()
        }
        
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}