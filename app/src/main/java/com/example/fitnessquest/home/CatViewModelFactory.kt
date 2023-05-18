package com.example.fitnessquest.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatViewModelFactory(private val resources: Resources )
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java))
            return CatViewModel(resources) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }

}