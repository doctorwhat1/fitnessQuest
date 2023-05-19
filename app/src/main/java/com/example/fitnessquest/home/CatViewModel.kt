package com.example.fitnessquest.home

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessquest.*
import java.util.*

class CatViewModel(
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    // current indicators
    private val _currentHP = MutableLiveData(0)
    val currentHP: LiveData<Int>
        get() = _currentHP

    private val _currentMana = MutableLiveData(0)
    val currentMana: LiveData<Int>
        get() = _currentMana


    // current values
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


    // rewards
    private val _isWeightRewardReceived = MutableLiveData(false)
    val isWeightRewardReceived: LiveData<Boolean>
        get() = _isWeightRewardReceived

    private val _isSleepTimeRewardReceived = MutableLiveData(false)
    val isSleepTimeRewardReceived: LiveData<Boolean>
        get() = _isSleepTimeRewardReceived

    
    fun setHP() {
        _currentHP.value = sharedPreferences.getInt(CURRENT_HP, 0)
    }
    
    fun setMana() {
        _currentMana.value = sharedPreferences.getInt(CURRENT_MANA, 0)
    }

    
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

    fun setIsSleepRewardReceived() {
        _isSleepTimeRewardReceived.value = sharedPreferences.getBoolean(IS_SLEEP_TIME_REWARD_RECEIVED, false)
    }

    fun setIsWeightRewardReceived() {
        _isWeightRewardReceived.value = sharedPreferences.getBoolean(IS_WEIGHT_REWARD_RECEIVED, false)
    }

}