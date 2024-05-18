package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

// Clase que representa la actividad de pantalla de inicio (splash screen)
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Crear un Handler para ejecutar una acción después de un retraso de 3 segundos (3000 milisegundos)
        Handler(Looper.getMainLooper()).postDelayed({
            // Crear un Intent para iniciar la LoginActivity
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            // Iniciar la LoginActivity
            startActivity(intent)
            // Finalizar la SplashActivity para que el usuario no pueda volver a ella
            finish()
        }, 3000)
    }
}
