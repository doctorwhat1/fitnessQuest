package com.example.fitnessquest.home

import android.content.SharedPreferences
import android.content.res.Resources
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatViewModelFactory(
    private val sensorManager: SensorManager,
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences
)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java))
            return CatViewModel(sensorManager, resources, sharedPreferences) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }

}