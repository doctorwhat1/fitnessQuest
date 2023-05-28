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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var saveButton : Button
    private lateinit var logoutButton : Button
    private lateinit var setting1EditText : EditText
    private lateinit var setting2EditText: EditText
    private lateinit var setting3EditText : EditText
    private lateinit var setting4EditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}