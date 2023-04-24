package com.example.fitnessquest

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [Cat.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cat : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var progressBarMana: ProgressBar
    private lateinit var progressBarHealth: ProgressBar

    private lateinit var increaseManaBTN : Button
    private lateinit var decreaseManaBTN : Button
    private lateinit var increaseHealthBTN : Button

    private lateinit var healthCounter: TextView
    private lateinit var manaCounter: TextView

    //var sharedPref: SharedPreferences? = null

    private val catStatusArray = arrayOf("Не готов", "Размялся", "Заряжен!")
    private val catDescriptionArray = arrayOf("Перед приключениями вашему котану нужно пополнить здоровье!", "Хорошая разминка помогает не только котикам, но и людям!", "Ваш кот готов покорить весь мир!")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//
//        val progressBarMana: ProgressBar = view.findViewById(R.id.progressBarMana)
//        progressBarMana.max = 2000
//        val currentProgressMana = 600
//
//        ObjectAnimator.ofInt(progressBarMana,"progress",currentProgressMana)
//            .setDuration(2000)
//            .start()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cat, container, false)



        progressBarMana = view.findViewById(R.id.progressBarMana)
        progressBarHealth = view.findViewById(R.id.progressBarHealth)

        var sharedPref = requireContext().getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        var editor=sharedPref.edit()

       // val myString = sharedPref?.getString("myStringKey", "defaultValue")

        progressBarMana.max = 7000
        progressBarHealth.max = 2000

        healthCounter = view.findViewById(R.id.healthCounter)
        manaCounter = view.findViewById(R.id.manaCounter2)


        progressBarHealth.getProgressDrawable().setColorFilter(
            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        progressBarMana.getProgressDrawable().setColorFilter(
            Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);



        // TODO: Change 'em to normal variables.



        // CLEAR SHAREDPREFS
        //editor.clear().commit()

        //var currentProgressMana = 2000
        var currentProgressMana = sharedPref.getInt("mp",0)

       // var tempString = sharedPref?.getString("health", "200").toString()
        var currentProgressHP = sharedPref.getInt("hp",0)
        //var currentProgressHP = 0


        setProgressBar(progressBarMana,currentProgressMana)
        setProgressBar(progressBarHealth,currentProgressHP)


        healthCounter.setText(currentProgressHP.toString())
        manaCounter.setText((currentProgressMana/1000).toString())
//        ObjectAnimator.ofInt(progressBarMana,"progress",currentProgressMana)
//            .setDuration(2000)
//            .start()
      //  healthCounter.setText("lol")

        // INCREASE HP

        increaseHealthBTN = view.findViewById(R.id.healthAdd)
        increaseHealthBTN.setOnClickListener()
        {
            //TODO Show Pop Up Menu and increase health.
            var builder = AlertDialog.Builder(activity)
            builder.setTitle("Упражнение")
            builder.setMessage("Выбранное упражнение необходимо выполнить в реальной жизни, в комфортном для вас темпе и соблюдая технику безопасности")
            builder.setPositiveButton("Выполнить", DialogInterface.OnClickListener {
                    dialog, id -> Toast.makeText(context,"Completed!", Toast.LENGTH_SHORT).show()
                dialog.cancel()
                // TODO Add HP Func. Change it so it adds dynamic amount of hp.
                currentProgressHP += 100
                setProgressBar(progressBarHealth, currentProgressHP)
                healthCounter.setText(currentProgressHP.toString())
                saveProgress("hp",currentProgressHP)
            })

            builder.setNegativeButton("Отмена", DialogInterface.OnClickListener {
                    dialog, id ->
                dialog.cancel()

            })
            var alert = builder.create()

            val popupMenu: PopupMenu = PopupMenu(context,increaseHealthBTN)
            popupMenu.menuInflater.inflate(R.menu.warmup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.pushupX5 -> {
                      //  Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                        //    .show()
                        alert = builder.create()
                        alert.show()

                    }
                    R.id.pushupX10 ->{
                     //   Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        alert = builder.create()
                        alert.show()
                    }

                    R.id.situpX5 -> {
                      //  Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        alert = builder.create()
                        alert.show()
                    }
                }
                true
            })
            popupMenu.show()
        }


        // INCREASE MANA BUTTON
        increaseManaBTN = view.findViewById(R.id.manaAdd)
        increaseManaBTN.setOnClickListener()
        {
            currentProgressMana += 1000
            if (currentProgressMana>7000)
                currentProgressMana -= 1000
            setProgressBar(progressBarMana,currentProgressMana)
            manaCounter.setText((currentProgressMana/1000).toString())
            saveProgress("mp",currentProgressMana)


            //TODO: Save to Shared Pref
        }

        // DECREASE MANA BUTTON
        decreaseManaBTN = view.findViewById(R.id.manaRemove)
        decreaseManaBTN.setOnClickListener()
        {
            currentProgressMana -= 1000
            if (currentProgressMana<0)
                currentProgressMana += 1000

            setProgressBar(progressBarMana,currentProgressMana)
            manaCounter.setText((currentProgressMana/1000).toString())
            saveProgress("mp",currentProgressMana)
        }



        return view
    }


    //change to public
    public fun saveProgress(key: String, value: Int)
    {
       // val sharedPref = requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        //val editor = sharedPref.edit()
        var sharedPref = requireContext().getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        var editor=sharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun setProgressBar(progressBar: ProgressBar,currentProgress: Int)
    {
        if (currentProgress<0)
        {
            return
        }
        ObjectAnimator.ofInt(progressBar,"progress",currentProgress)
            .setDuration(750)
            .start()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Cat.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cat().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}