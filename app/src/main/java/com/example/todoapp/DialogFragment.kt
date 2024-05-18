package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

// Clase que representa un fragmento de diálogo para agregar una nueva tarea
class DialogFragment : DialogFragment() {

    // Interfaz para comunicar el resultado del diálogo a la actividad
    interface OnDialogResultListener {
        fun onDialogResult(taskName: String, tag: String)
    }

    // Variable para almacenar el listener
    private var listener: OnDialogResultListener? = null

    // Método para inflar la vista del diálogo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del diálogo
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)

        // Obtener referencias a los elementos de la interfaz
        val etTaskName = view.findViewById<EditText>(R.id.etTaskName)
        val rbTagGroup = view.findViewById<RadioGroup>(R.id.rbTagGroup)

        // Configuración del botón OK
        view.findViewById<Button>(R.id.btnOk).setOnClickListener {
            // Obtener el nombre de la tarea ingresado por el usuario
            val taskName = etTaskName.text.toString()
            // Obtener la etiqueta seleccionada
            val tag = when (rbTagGroup.checkedRadioButtonId) {
                R.id.rbWork -> "Work"
                R.id.rbPersonal -> "Personal"
                R.id.rbFitness -> "Fitness"
                else -> ""
            }

            // Notificar al listener con los datos ingresados por el usuario
            listener?.onDialogResult(taskName, tag)

            // Cerrar el diálogo
            dismiss()
        }

        // Configuración del botón Cancel
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            // Cerrar el diálogo
            dismiss()
        }

        return view
    }

    // Método para establecer el listener desde la actividad
    fun setOnDialogResultListener(listener: OnDialogResultListener) {
        this.listener = listener
    }
}
