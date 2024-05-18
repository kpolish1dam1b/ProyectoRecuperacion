package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// Clase de la actividad de registro de usuario
class SignUpActivity : AppCompatActivity() {
    // Declaración de variables para los elementos de la interfaz
    private lateinit var name: EditText
    private lateinit var user: EditText
    private lateinit var pswd: EditText
    private lateinit var pswd2: EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Inicialización de los elementos de la interfaz
        btnSignUp = findViewById(R.id.btnSign)
        name = findViewById(R.id.etName)
        user = findViewById(R.id.etUsername)
        pswd = findViewById(R.id.etSignUpPassword)
        pswd2 = findViewById(R.id.etRepeatPassword)

        // Configuración del botón de registro
        btnSignUp.setOnClickListener {
            // Comprueba si todos los campos no están vacíos
            if (!isBlankOrEmpty(name.text.toString()) && !isBlankOrEmpty(user.text.toString()) && !isBlankOrEmpty(pswd.text.toString()) && !isBlankOrEmpty(pswd2.text.toString())) {
                // Comprueba si las contraseñas coinciden
                if (pswd.text.toString() == pswd2.text.toString()) {
                    // Inicia la MainActivity si las contraseñas coinciden
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish() // Finaliza la actividad actual
                } else {
                    // Muestra un mensaje si las contraseñas no coinciden
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Muestra un mensaje si algún campo está vacío
                Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para comprobar si una cadena está vacía o en blanco
    fun isBlankOrEmpty(input: String): Boolean {
        return input.isBlank() || input.isEmpty()
    }
}
