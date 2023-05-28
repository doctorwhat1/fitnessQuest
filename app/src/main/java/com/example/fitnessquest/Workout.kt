package com.example.fitnessquest

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class Workout : AppCompatActivity(), SensorEventListener {


    // надо бы это как то свернуть
    val json_EXAMPLE = "{\n" +
            "  \"Warm Up\": [\n" +
            "    {\n" +
            "      \"Exercise\": \"Jumping Jacks\",\n" +
            "      \"Time\": \"2 minutes\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Arm Circles\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Exercises\": [\n" +
            "    {\n" +
            "      \"Exercise\": \"Pushups\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"15\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Chair Dips\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"15\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Bicep Curls\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"15\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Hammer Curls\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"15\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Reverse Curls\",\n" +
            "      \"Sets\": \"3\",\n" +
            "      \"Reps\": \"15\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Cool Down\": [\n" +
            "    {\n" +
            "      \"Exercise\": \"Shoulder Stretch\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Tricep Stretch\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Exercise\": \"Bicep Stretch\",\n" +
            "      \"Time\": \"1 minute\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"key\": \"biceps-10-home-none\",\n" +
            "  \"_id\": \"644ccc609a14eaa0fdda995d\"\n" +
            "}"


//    val client = OkHttpClient()
//
//    val request = Request.Builder()
//        .url("https://workout-planner1.p.rapidapi.com/?time=10&muscle=biceps&location=home&equipment=none")
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





    lateinit var sensorManager: SensorManager


    val json_response = json_EXAMPLE
    //ACTUAL CODE BELOW, ADD THREADS OR COROUTINES
    //val json_response = response.toString()

    val jsonObject1 = JSONObject(json_response)

    val jsonArray1 = jsonObject1.getJSONArray("Exercises")

    val arrayLen = jsonArray1.length()

    //val jsonArray1 = JSONArray(json_response)


    private lateinit var cancelBTN : Button
    private lateinit var startBTN : Button
    private lateinit var exerciseName : TextView
    private lateinit var exerciseReps : TextView
    private lateinit var timerTXT : TextView
    private lateinit var accelerometer_data : TextView

    private lateinit var bossHealthBar : ProgressBar
    private lateinit var bossHealthText : TextView

    private lateinit var workoutGif : GifImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)


//translation
        val englishMap = HashMap<String, String>()
        englishMap["Pushups"] = "Отжимания"
        englishMap["Hammer Curls"] = "Подъём гантелей на бицепс"
        englishMap["Concentration Curls"] = "Сидячий подъём гантелей"
        englishMap["Bicep Curls"] = "Сгибание рук на бицепс"
        englishMap["Chair Dips"] = "Отжимания с упором на стуле"
        englishMap["Reverse Curls"] = "Сгибания с обратным хватом"
        englishMap["Decline Pushup"] = "Отжимания под углом"
        englishMap["Triceps Dips"] = "Отжимания на трицепс"


//pictures array

        val workoutMap = HashMap<String, Int>()
        workoutMap["Pushups"] = R.drawable.pushup1
        workoutMap["Concentration Curls"] = R.drawable.concentration_curl
        workoutMap["Decline Pushup"] = R.drawable.decline_pushup
        workoutMap["Hammer Curls"] = R.drawable.hammer_curl2
        workoutMap["Reverse Curls"] = R.drawable.reverse_curl
        workoutMap["Chair Dips"] = R.drawable.chair_dips2
        workoutMap["Bicep Curls"] = R.drawable.bicep_curls
        workoutMap["Triceps Dips"] = R.drawable.tricep_dips


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

        bossHealthBar = findViewById(R.id.bossHealthBar)
        bossHealthText = findViewById(R.id.bossHealthText)
        var bossCurrentHP = 100

        accelerometer_data = findViewById(R.id.accelerometerTXT)

//        timerTXT = findViewById(R.id.timerText)
//        timerTXT.visibility = View.INVISIBLE

        workoutGif = findViewById(R.id.workoutGIF)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )


        var gifname = "pushup"

        var gifFromResource = GifDrawable(resources, R.drawable.pushup1)

        // Works!!!
        workoutGif.setImageDrawable(gifFromResource)



        fun setExerciseGif()
        {
            //todo fix dis stuff
            var jsonObject1 = jsonArray1.getJSONObject(step)
            var exname = workoutMap.get(jsonObject1.getString("Exercise"))
            if (exname == null)
            {
                return
            }
            var gifFromResource = GifDrawable(resources, exname)
            workoutGif.setImageDrawable(gifFromResource)
        }

        fun setExerciseText()
        {
           var jsonObject1 = jsonArray1.getJSONObject(step)
            /// exerciseName.setText(jsonObject1.getString("Exercise"))
            exerciseName.setText(englishMap.get(jsonObject1.getString("Exercise")))
            exerciseReps.setText(jsonObject1.getString("Reps"))
            step+=1

            if (exerciseName.text.isEmpty()){
                exerciseName.text = jsonObject1.getString("Exercise")
            }

        }


        fun damageBossHealth()
        {
            bossCurrentHP -= (100 / arrayLen)
           // bossHealthBar.progress = bossCurrentHP
            //variant below has smooth animation
            setProgressBar(bossHealthBar,bossCurrentHP)
            bossHealthText.text = bossCurrentHP.toString()
        }

//        // TODO move to setExerciseText()
//        var jsonObject1 = jsonArray1.getJSONObject(step)
//        // !!! exerciseName.setText(jsonObject1.getString("Exercise"))
//        exerciseName.setText(englishMap.get(jsonObject1.getString("Exercise")))
//        exerciseReps.setText(jsonObject1.getString("Reps"))
//        step+=1
//        //
        setExerciseText()



        startBTN = findViewById(R.id.startExerciseButton)
        startBTN.setOnClickListener(){
            if (step<arrayLen) {

                setExerciseGif()
                setExerciseText()

                //TODO add SetExerciseGif()
                damageBossHealth()
//                jsonObject1 = jsonArray1.getJSONObject(step)
//                /// exerciseName.setText(jsonObject1.getString("Exercise"))
//                exerciseName.setText(englishMap.get(jsonObject1.getString("Exercise")))
//                exerciseReps.setText(jsonObject1.getString("Reps"))
//                step+=1
//                //
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




//        fun finishActivity()
//        {
//            var editor=sharedPref.edit()
//            editor.putInt("lvl", currentLevel+1)
//            editor.apply()
//        }



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

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    override fun onSensorChanged(event: SensorEvent?) {
        accelerometer_data.text = "x = ${event!!.values[0]} \n y = ${event!!.values[1]} \n z = ${event!!.values[2]} \n "

        // just sum it up.
        // if sum is greater than some constant = user is doing the actual exercise
        //TODO: Добавить проверку.
    }
}



// в активити попадает текущий уровень
// в заисимости от уровня генерируется количество упражнений.

// распаковываем упражнения
// по истечению таймера смотрим есть ли ещё упражнения. Если есть - загружаем снова новое упражнение
// если нет - ставим флаги, повышаем уровень и прочее и уходим в мейн
//