package com.example.fitnessquest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var gotoRegistryBTN : Button
    private lateinit var loginBTN : Button

    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        firebaseAuth = FirebaseAuth.getInstance()

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)


        // if user is already logged in
        if (sharedPref.getString("user","none").toString() != "none") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        loginBTN = findViewById(R.id.loginBTN)
        loginBTN.setOnClickListener()
        {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()



            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        editor.putString("user", email)
                        editor.apply()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }



        }


        gotoRegistryBTN = findViewById(R.id.gotoRegisterBTN)
        gotoRegistryBTN.setOnClickListener()
        {
            val signupIntent = Intent(this, SignupActivity::class.java)
            startActivity(signupIntent)
        }
    }
}