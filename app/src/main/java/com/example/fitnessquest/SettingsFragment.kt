package com.example.fitnessquest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class SettingsFragment : Fragment() {

    private lateinit var saveButton : Button
    private lateinit var logoutButton : Button
    private lateinit var setting1EditText : EditText
    private lateinit var setting2EditText: EditText
    private lateinit var setting3EditText : EditText
    private lateinit var setting4EditText : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val sharedPref = requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // fill editexts with data from sharedprefs
        setting1EditText = view.findViewById(R.id.input_steps_daily)
        setting1EditText.setText(sharedPref.getInt("steps_daily",7000).toString())


        setting2EditText = view.findViewById(R.id.input_calories_daily)
        setting2EditText.setText(sharedPref.getInt("calories_daily",2000).toString())




        // save to shared pref button

        saveButton = view.findViewById(R.id.button_save)
        saveButton.setOnClickListener()
        {

            var steps_daily = setting1EditText.text.toString().toInt()
            editor.putInt("steps_daily", steps_daily)
            //saveToSharedPref("steps_daily",steps_daily)


            var calories_daily = setting2EditText.text.toString().toInt()
            editor.putInt("calories_daily", calories_daily)



            editor.apply()
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

        }


        //logout button
        logoutButton = view.findViewById(R.id.button_logout)
        logoutButton.setOnClickListener()
        {
            editor.putString("user", "none")
            editor.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)



        }




        return view
    }
//
//    private fun saveToSharedPref(key: String, value: Int)
//    {
//        var sharedPref = requireContext().getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
//        var editor=sharedPref.edit()
//        editor.putInt(key, value)
//        editor.apply()
//    }

}