package com.example.fitnessquest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fitnessquest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup bottom navigation view with navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        // FOR TESTING !!! DELETE ON RELEASE
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(CURRENT_HP, 28)
        editor.putFloat(INITIAL_TOTAL_STEPS, 0f)
        editor.putInt(CURRENT_WATER_CUPS, 0)
        editor.putInt(CURRENT_ACTIVITY_MINS, 0)
        editor.putString(CURRENT_WEIGHT, resources.getString(R.string.cat_weight_template))
        editor.putString(FALLING_ASLEEP_TIME, resources.getString(R.string.cat_time_template))
        editor.putString(WAKEUP_TIME, resources.getString(R.string.cat_time_template))
        editor.putString(BREAKFAST_CALORIES, "0")
        editor.putString(LUNCH_CALORIES, "0")
        editor.putString(DINNER_CALORIES, "0")
        editor.putBoolean(IS_BREAKFAST_ENTERED, false)
        editor.putBoolean(IS_LUNCH_ENTERED, false)
        editor.putBoolean(IS_DINNER_ENTERED, false)
        editor.putString(TOTAL_CALORIES, "0")
        editor.apply()

    }

}