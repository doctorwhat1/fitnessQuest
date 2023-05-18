package com.example.fitnessquest.home

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessquest.R
import java.util.*

class CatViewModel(private val resources: Resources) : ViewModel() {

    private val _currentWaterCups = MutableLiveData(0)
    val currentWaterCups: LiveData<Int>
        get() = _currentWaterCups

    fun increaseWaterCups() {
        _currentWaterCups.value = (_currentWaterCups.value ?: 0) + 1
    }

    fun decreaseWaterCups() {
        if (currentWaterCups.value == 0) return
        _currentWaterCups.value =(_currentWaterCups.value ?: 0) - 1
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