package com.example.fitnessquest.home

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessquest.*
import java.util.*

class CatViewModel(
    private val sensorManager: SensorManager?,
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences
) : ViewModel(), SensorEventListener {

    // ------------------ STEP COUNTER ------------------------------------------------------------------
    private var initialTotalSteps = sharedPreferences.getFloat(INITIAL_TOTAL_STEPS, 0f) // initial total steps

    // current indicators
    private val _currentSteps = MutableLiveData(0)
    val currentSteps: LiveData<Int>
        get() = _currentSteps

    override fun onSensorChanged(event: SensorEvent?) {
        val totalSteps = event!!.values[0]

        if (initialTotalSteps == 0f) {
            initialTotalSteps = totalSteps // initialize
            // save initial total steps to shared preferences
            val editor = sharedPreferences.edit()
            editor.putFloat(INITIAL_TOTAL_STEPS, initialTotalSteps)
            editor.apply()
        }

        val prevCurrentSteps = _currentSteps.value!!
        _currentSteps.value = (totalSteps - initialTotalSteps).toInt()
        if (prevCurrentSteps < 6000 && _currentSteps.value!! >= 6000) {
            _currentHP.value = _currentHP.value!! + 25
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    fun onResumeStepCounter() {
        // Returns the number of steps taken by the user since the last reboot while activated
        // This sensor requires permission android.permission.ACTIVITY_RECOGNITION.
        // So don't forget to add the following permission in AndroidManifest.xml present in manifest folder of the app.
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // Rate suitable for the user interface
        sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
    }
    // ------------------------------------------------------------------------------------------------


    // current HP
    private val _currentHP = MutableLiveData(0)
    val currentHP: LiveData<Int>
        get() = _currentHP

    // current values
    private val _totalCalories = MutableLiveData("0")
    val totalCalories: LiveData<String>
        get() = _totalCalories
    
    private val _currentActivityMins = MutableLiveData(0)
    val currentActivityMins: LiveData<Int>
        get() = _currentActivityMins
    
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
    private val _isBreakfastEntered = MutableLiveData(false)
    val isBreakfastEntered: LiveData<Boolean>
        get() = _isBreakfastEntered

    private val _isLunchEntered = MutableLiveData(false)
    val isLunchEntered: LiveData<Boolean>
        get() = _isLunchEntered

    private val _isDinnerEntered = MutableLiveData(false)
    val isDinnerEntered: LiveData<Boolean>
        get() = _isDinnerEntered

    
    fun setHP() {
        _currentHP.value = sharedPreferences.getInt(CURRENT_HP, 0)
    }

    

    fun setTotalCalories() {
        _totalCalories.value = sharedPreferences.getString(TOTAL_CALORIES, "0")
    }

    fun setCurrentActivity() {
        _currentActivityMins.value = sharedPreferences.getInt(CURRENT_ACTIVITY_MINS, 0)
    }

    fun setSleepTime() {
        _fallingAsleepTime.value = sharedPreferences.getString(FALLING_ASLEEP_TIME, resources.getString(R.string.cat_time_template))
        _wakeupTime.value = sharedPreferences.getString(WAKEUP_TIME, resources.getString(R.string.cat_time_template))
    }

    fun setCurrentWeight() {
        _currentWeight.value = sharedPreferences.getString(CURRENT_WEIGHT, resources.getString(R.string.cat_weight_template))
    }

    fun increaseWaterCups() {
        _currentWaterCups.value = (_currentWaterCups.value ?: 0) + 1
        if (_currentHP.value!! > 100) return

        if ((_currentWaterCups.value ?: 0) == 8) {
            _currentHP.value = if (_currentHP.value!! >= 75) 100 else _currentHP.value!! + 25
            val editor = sharedPreferences.edit()
            editor.putInt(CURRENT_HP, _currentHP.value!!)
            editor.apply()
        }
    }

    fun decreaseWaterCups() {
        if (currentWaterCups.value == 0) return
        if ((_currentWaterCups.value ?: 0) == 8) {
            _currentHP.value = _currentHP.value!! - 25
            val editor = sharedPreferences.edit()
            editor.putInt(CURRENT_HP, _currentHP.value!!)
            editor.apply()
        }
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
    
    fun setIsBreakfastEntered() {
        _isBreakfastEntered.value = sharedPreferences.getBoolean(IS_BREAKFAST_ENTERED, false)
    }

    fun setIsLunchEntered() {
        _isLunchEntered.value = sharedPreferences.getBoolean(IS_LUNCH_ENTERED, false)
    }

    fun setIsDinnerEntered() {
        _isDinnerEntered.value = sharedPreferences.getBoolean(IS_DINNER_ENTERED, false)
    }

    fun increaseHP() {
        if (_currentHP.value == 100) return

        val editor = sharedPreferences.edit()
        _currentHP.value = if (_currentHP.value!! > 75) 100 else _currentHP.value!! + 25
        editor.putInt(CURRENT_HP, _currentHP.value!!)
        editor.apply()
    }

}