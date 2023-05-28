package com.example.fitnessquest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitnessquest.databinding.FragmentBattlesBinding

//import com.example.fitnessquest.home.CatFragment


class BattlesFragment : Fragment() {
    // view binding
    private var _binding: FragmentBattlesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBattlesBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPref = requireContext().getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        val lvl = sharedPref.getInt("lvl",1)
        binding.tvCurrentFight.text = "Битва №$lvl"

        binding.pbPersonHp.max = 100
        binding.pbPersonHp.progress = sharedPref.getInt(CURRENT_HP, 0)
        binding.tvHpProgress.text = "${sharedPref.getInt(CURRENT_HP, 0).toString()}/100HP"

        binding.btnStartBattle.setOnClickListener {
            // add HP and mana check
            Toast.makeText(context, "WorkoutActivity Start!", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, WorkoutActivity::class.java)
            startActivity(intent)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}