package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var user : EditText
    private lateinit var pswd : EditText
    private lateinit var btnSignUp : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        user = findViewById(R.id.etUser)
        pswd = findViewById(R.id.etPassword)

        btnLogin.setOnClickListener{
            if (!isBlankOrEmpty(user.text.toString()) && !isBlankOrEmpty(pswd.text.toString())){
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }else {
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignUp.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
            this.finish()
        }

    }

    fun isBlankOrEmpty(input: String): Boolean {
        return input.isBlank() || input.isEmpty()
    }
}