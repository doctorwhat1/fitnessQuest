package com.example.fitnessquest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.security.AccessController.getContext


class Workout : AppCompatActivity() {


    // надо бы это как то свернуть
    val json_EXAMPLE = "{\n" +
            "  \"Warm Up\": [\n" +
            "    {\n" +
            "      \"Exercise\": \"Jumping Jacks\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Arm Circles\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Exercises\": [\n" +
            "    {\n" +
            "      \"Exercise\": \"Hammer Curls\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"10\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Concentration Curls\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"20\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Bicep Curls\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"30\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Cool Down\": [\n" +
            "    {\n" +
            "      \"Exercise\": \"Tricep Stretch\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Shoulder Stretch\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"key\": \"biceps-1-home-undefined\",\n" +
            "  \"_id\": \"643e75e995227eff16a33050\"\n" +
            "}\n"


//    val client = OkHttpClient()
//
//    val request = Request.Builder()
//        .url("https://workout-planner1.p.rapidapi.com/?time=30&muscle=biceps&location=gym&equipment=dumbbells")
//        .get()
//        .addHeader("X-RapidAPI-Key", "9c4dc0a8ebmshc4b3d4d062067b5p14740bjsn7a05f1b9b925")
//        .addHeader("X-RapidAPI-Host", "workout-planner1.p.rapidapi.com")
//        .build()
//
//    val response = client.newCall(request).execute()

   // var sharedPref = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
   // var sharedPref = getSharedPreferences(resources.getString(R.string.app_name), getContext())

    //TODO: Change to sharedpreference

    // do i need it? i do, yeah
   // var currentLevel = sharedPref.getInt("lvl", 1)

    var step = 0



    val json_response = json_EXAMPLE

    val jsonObject1 = JSONObject(json_response)

    val jsonArray1 = jsonObject1.getJSONArray("Exercises")

    val arrayLen = jsonArray1.length()

    //val jsonArray1 = JSONArray(json_response)


    private lateinit var cancelBTN : Button
    private lateinit var startBTN : Button
    private lateinit var exerciseName : TextView
    private lateinit var exerciseReps : TextView
    private lateinit var timerTXT : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)


//        for (i in 0 until jsonArray1.length())
//        {
//            val jsonObject1 = jsonArray1.getJSONObject(i)
//            val exerciseName = jsonObject1.getString("Exercise")
//            val exerciseReps = jsonObject1.getString("Reps")
//            println(exerciseName)
//            println(exerciseReps)
//        }

        var sharedPref = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        var currentLevel = sharedPref.getInt("lvl", 1)


        exerciseName = findViewById(R.id.exerciseText)
        exerciseReps = findViewById(R.id.exerciseRepsText)

        timerTXT = findViewById(R.id.timerText)
        timerTXT.visibility = View.INVISIBLE

        // TODO move to setExerciseText()
        var jsonObject1 = jsonArray1.getJSONObject(step)
        exerciseName.setText(jsonObject1.getString("Exercise"))
        exerciseReps.setText(jsonObject1.getString("Reps"))
        step+=1
        //



        startBTN = findViewById(R.id.startWorkoutButton)
        startBTN.setOnClickListener(){
            if (step<arrayLen) {

                // TODO move to setExerciseText()
                jsonObject1 = jsonArray1.getJSONObject(step)
                exerciseName.setText(jsonObject1.getString("Exercise"))
                exerciseReps.setText(jsonObject1.getString("Reps"))
                step+=1
                //
            }
            else {
                //Todo: ActivityFinish Func

                var editor=sharedPref.edit()
                editor.putInt("lvl", currentLevel+1)
                editor.putInt("hp", 0)
                editor.apply()

                //TODO: Generate enemy name here.

                //TODO: Generate Requirements for next battle.

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            // ЕСЛИ ТЕКУЩИЙ ШАГ ВНУТРИ ДЛИННЫ АРРЕЯ, ТО ИДЁМ НА СЛЕД ШАГ. ЕСЛИ НЕТ - ФУНКЦИЯ ВОРКАУТ ФИНИШ И ВЫХОД.

        }


        cancelBTN = findViewById(R.id.cancelWorkoutButton)
        cancelBTN.setOnClickListener()
        {
            //return to main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        fun setExerciseText()
        {
//            jsonObject1 = jsonArray1.getJSONObject(step)
//            exerciseName.setText(jsonObject1.getString("Exercise"))
//            exerciseReps.setText(jsonObject1.getString("Reps"))
//            step+=1

        }

//        fun finishActivity()
//        {
//            var editor=sharedPref.edit()
//            editor.putInt("lvl", currentLevel+1)
//            editor.apply()
//        }



    }
}



// в активити попадает текущий уровень
// в заисимости от уровня генерируется количество упражнений.

// распаковываем упражнения
// по истечению таймера смотрим есть ли ещё упражнения. Если есть - загружаем снова новое упражнение
// если нет - ставим флаги, повышаем уровень и прочее и уходим в мейн
//