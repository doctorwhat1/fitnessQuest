package com.example.fitnessquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var createAccountBTN : Button

    private lateinit var registryEmail : EditText
    private lateinit var registryPassword : EditText
    private lateinit var registryPasswordConfirm : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        firebaseAuth = FirebaseAuth.getInstance()

        registryEmail = findViewById(R.id.registryEmail)
        registryPassword = findViewById(R.id.registryPassword)
        registryPasswordConfirm = findViewById(R.id.registryPasswordConfirm)






        createAccountBTN = findViewById(R.id.createAccountBTN)
        createAccountBTN.setOnClickListener()
        {
            val email = registryEmail.text.toString()
            val password = registryPassword.text.toString()
            val confirmPassword = registryPasswordConfirm.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if (password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }


        }

//        loginRedirectText.setOnClickListener {
//            val loginIntent = Intent(this, LoginActivity::class.java)
//            startActivity(loginIntent)
//        }


    }
}





//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import com.example.loginsignupauth.databinding.ActivitySignupBinding
//import com.google.firebase.auth.FirebaseAuth
//class SignupActivity : AppCompatActivity() {
//    private lateinit var binding: ActivitySignupBinding
//    private lateinit var firebaseAuth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignupBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        firebaseAuth = FirebaseAuth.getInstance()
//        binding.signupButton.setOnClickListener{
//            val email = binding.signupEmail.text.toString()
//            val password = binding.signupPassword.text.toString()
//            val confirmPassword = binding.signupConfirm.text.toString()
//            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
//                if (password == confirmPassword){
//                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
//                        if (it.isSuccessful){
//                            val intent = Intent(this, LoginActivity::class.java)
//                            startActivity(intent)
//                        } else {
//                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                } else {
//                    Toast.makeText(this, "Password does not matched", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
//            }
//        }
//        binding.loginRedirectText.setOnClickListener {
//            val loginIntent = Intent(this, LoginActivity::class.java)
//            startActivity(loginIntent)
//        }
//    }
//}