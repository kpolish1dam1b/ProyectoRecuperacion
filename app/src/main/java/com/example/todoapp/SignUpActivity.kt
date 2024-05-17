package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    private lateinit var name : EditText
    private lateinit var user : EditText
    private lateinit var pswd : EditText
    private lateinit var pswd2 : EditText
    private lateinit var btnSignUp : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp = findViewById(R.id.btnSign)
        name = findViewById(R.id.etName)
        user = findViewById(R.id.etUsername)
        pswd = findViewById(R.id.etSignUpPassword)
        pswd2 = findViewById(R.id.etRepeatPassword)

        btnSignUp.setOnClickListener{
            if (!isBlankOrEmpty(name.text.toString()) && !isBlankOrEmpty(user.text.toString()) && !isBlankOrEmpty(pswd.text.toString()) && !isBlankOrEmpty(pswd2.text.toString())){
                if (pswd.text.toString().contentEquals(pswd2.text.toString())){
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }else{
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }

            }else {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isBlankOrEmpty(input: String): Boolean {
        return input.isBlank() || input.isEmpty()
    }
}