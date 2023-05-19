package com.example.fitnessquest.home.cards

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitnessquest.APP_PREFERENCES
import com.example.fitnessquest.CURRENT_WEIGHT
import com.example.fitnessquest.IS_WEIGHT_REWARD_RECEIVED
import com.example.fitnessquest.R
import com.example.fitnessquest.databinding.FragmentWeightBinding
import com.example.fitnessquest.home.CatViewModel


class WeightFragment : Fragment() {

    // view binding
    private var _binding: FragmentWeightBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeightBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnEnter.setOnClickListener {
            val weight = binding.edittextEditWeight.text.toString()
            if (weight.toDouble() < 40 || weight.toDouble() > 200) {
                Toast.makeText(context, "Введите корректный вес", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val editor =  sharedPref.edit()
            editor.putString(CURRENT_WEIGHT, weight)
            editor.putBoolean(IS_WEIGHT_REWARD_RECEIVED, true)
            editor.apply()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}