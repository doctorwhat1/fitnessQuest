package com.example.fitnessquest.home.cards

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitnessquest.*
import com.example.fitnessquest.databinding.FragmentWeightBinding



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
            if (weight.toDouble() < 40.0 || weight.toDouble() > 200.0) {
                Toast.makeText(context, "Введите корректный вес", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val sharedPref = requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val editor =  sharedPref.edit()

            val weightTemplate = resources.getString(R.string.cat_weight_template)
            if (sharedPref.getString(CURRENT_WEIGHT, weightTemplate) == weightTemplate) {
                editor.putInt(CURRENT_HP, (sharedPref.getInt(CURRENT_HP, 0) + 25))
            }
            editor.putString(CURRENT_WEIGHT, weight)

            editor.apply()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}