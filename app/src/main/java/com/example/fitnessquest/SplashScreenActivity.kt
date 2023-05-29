package com.example.fitnessquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SC: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(  {
            //TODO: add check whether user is logged in or not
            startActivity(Intent (this, LoginActivity::class.java))
            finish()
        }, SPLASH_SC)
        /*
        */
    }
}