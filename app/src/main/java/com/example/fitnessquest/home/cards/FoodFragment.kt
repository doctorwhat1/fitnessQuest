package com.example.fitnessquest.home.cards

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessquest.*
import com.example.fitnessquest.databinding.FragmentFoodBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class FoodFragment : Fragment() {

    // view binding
    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    private val _breakfastCalories = MutableLiveData("0")
    val breakfastCalories: LiveData<String>
        get() = _breakfastCalories

    private val _lunchCalories = MutableLiveData("0")
    val lunchCalories: LiveData<String>
        get() = _lunchCalories

    private val _dinnerCalories = MutableLiveData("0")
    val dinnerCalories: LiveData<String>
        get() = _dinnerCalories

    private val _totalCalories = MutableLiveData("0")
    val totalCalories: LiveData<String>
        get() = _totalCalories
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.foodFragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        val sharedPreferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        _breakfastCalories.value = sharedPreferences.getString(BREAKFAST_CALORIES, "0")
        _lunchCalories.value = sharedPreferences.getString(LUNCH_CALORIES, "0")
        _dinnerCalories.value = sharedPreferences.getString(DINNER_CALORIES, "0")
        _totalCalories.value = sharedPreferences.getString(TOTAL_CALORIES, "0")


        binding.btnEnterCaloriesFf.setOnClickListener {
            val enteredCalories = binding.etEditCalories.text.toString()

            if (enteredCalories.isEmpty() || enteredCalories.toDouble() < 0.0 || enteredCalories.toDouble() > 1000.0) {
                Toast.makeText(context, "Введите корректное число калорий", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val meal = binding.rgMeals.checkedRadioButtonId
            
            val editor =  sharedPreferences.edit()

            var totalCaloriesToRemove = 0.0
            when(meal) {
                R.id.rbtn_breakfast -> {
                    if (!sharedPreferences.getBoolean(IS_BREAKFAST_ENTERED, false)) {
                        editor.putInt(CURRENT_HP, sharedPreferences.getInt(CURRENT_HP, 0) + 10)
                    }
                    editor.putBoolean(IS_BREAKFAST_ENTERED, true)
                    totalCaloriesToRemove = _breakfastCalories.value!!.toDouble()
                    editor.putString(BREAKFAST_CALORIES, enteredCalories)
                    _breakfastCalories.value = enteredCalories
                }
                R.id.rbtn_lunch -> {
                    if (!sharedPreferences.getBoolean(IS_LUNCH_ENTERED, false)) {
                        editor.putInt(CURRENT_HP, sharedPreferences.getInt(CURRENT_HP, 0) + 10)
                    }
                    editor.putBoolean(IS_LUNCH_ENTERED, true)
                    totalCaloriesToRemove = _lunchCalories.value!!.toDouble()
                    editor.putString(LUNCH_CALORIES, enteredCalories)
                    _lunchCalories.value = enteredCalories
                }
                R.id.rbtn_dinner -> {
                    if (!sharedPreferences.getBoolean(IS_DINNER_ENTERED, false)) {
                        editor.putInt(CURRENT_HP, sharedPreferences.getInt(CURRENT_HP, 0) + 10)
                    }
                    editor.putBoolean(IS_DINNER_ENTERED, true)
                    totalCaloriesToRemove = _dinnerCalories.value!!.toDouble()
                    editor.putString(DINNER_CALORIES, enteredCalories)
                    _dinnerCalories.value = enteredCalories
                }
                else -> Toast.makeText(context, "Выберите прием пищи для ввода калорий", Toast.LENGTH_SHORT).show()
            }

            val df = DecimalFormat("#.##")
            df.decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
            val totalCaloriesDouble = _totalCalories.value!!.toDouble() + enteredCalories.toDouble() - totalCaloriesToRemove
            _totalCalories.value = df.format(totalCaloriesDouble)
            editor.putString(TOTAL_CALORIES, _totalCalories.value)

            editor.apply()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}