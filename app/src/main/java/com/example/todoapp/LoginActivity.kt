package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// Clase de la actividad de inicio de sesión
class LoginActivity : AppCompatActivity() {

    // Declaración de variables para los elementos de la interfaz
    private lateinit var btnLogin: Button
    private lateinit var user: EditText
    private lateinit var pswd: EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicialización de los elementos de la interfaz
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        user = findViewById(R.id.etUser)
        pswd = findViewById(R.id.etPassword)

        // Configuración del botón de inicio de sesión
        btnLogin.setOnClickListener {
            // Comprueba si los campos de usuario y contraseña no están vacíos
            if (!isBlankOrEmpty(user.text.toString()) && !isBlankOrEmpty(pswd.text.toString())) {
                // Inicia la MainActivity si los campos no están vacíos
                startActivity(Intent(this, MainActivity::class.java))
                this.finish() // Finaliza la actividad actual
            } else {
                // Muestra un mensaje si los campos están vacíos
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuración del botón de registro
        btnSignUp.setOnClickListener {
            // Inicia la SignUpActivity
            startActivity(Intent(this, SignUpActivity::class.java))
            this.finish() // Finaliza la actividad actual
        }
    }

    // Función para comprobar si una cadena está vacía o en blanco
    fun isBlankOrEmpty(input: String): Boolean {
        return input.isBlank() || input.isEmpty()
    }
}
