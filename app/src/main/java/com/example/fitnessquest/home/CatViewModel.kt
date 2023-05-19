package com.example.fitnessquest.home

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessquest.CURRENT_WEIGHT
import com.example.fitnessquest.FALLING_ASLEEP_TIME
import com.example.fitnessquest.R
import com.example.fitnessquest.WAKEUP_TIME
import java.util.*

class CatViewModel(
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _currentWaterCups = MutableLiveData(0)
    val currentWaterCups: LiveData<Int>
        get() = _currentWaterCups

    private val _fallingAsleepTime = MutableLiveData("**:**")
    val fallingAsleepTime: LiveData<String>
        get() = _fallingAsleepTime

    private val _wakeupTime = MutableLiveData("**:**")
    val wakeupTime: LiveData<String>
        get() = _wakeupTime

    private val _currentWeight = MutableLiveData("--")
    val currentWeight: LiveData<String>
        get() = _currentWeight



    /*fun setCurrentStrolls() {
        TODO("Not yet implemented")
    }*/

    /*fun setCurrentCalories() {
        TODO("Not yet implemented")
    }*/

    /*fun setCurrentActivity() {
        TODO("Not yet implemented")
    }*/

    fun setSleepTime() {
        if (sharedPreferences.contains(FALLING_ASLEEP_TIME)
            && sharedPreferences.contains(WAKEUP_TIME)) {
            _fallingAsleepTime.value = sharedPreferences.getString(FALLING_ASLEEP_TIME, "**-**")
            _wakeupTime.value = sharedPreferences.getString(WAKEUP_TIME, "**-**")
        }
    }

    fun setCurrentWeight() {
        if (sharedPreferences.contains(CURRENT_WEIGHT)) {
            _currentWeight.value = sharedPreferences.getString(CURRENT_WEIGHT, "")
        }
    }

    fun increaseWaterCups() {
        _currentWaterCups.value = (_currentWaterCups.value ?: 0) + 1
    }

    fun decreaseWaterCups() {
        if (currentWaterCups.value == 0) return
        _currentWaterCups.value = (_currentWaterCups.value ?: 0) - 1
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DATE)
        val month = resources
            .getStringArray(R.array.months)[calendar.get(Calendar.MONTH)]
        /* in calendar days indexes are in range from 1 to 7
        and week starts on sunday and ends on saturday */
        val dayIndex = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
        val dayOfWeek = resources
            .getStringArray(R.array.days_of_week)[dayIndex]

        return "$day $month, $dayOfWeek"
    }

}