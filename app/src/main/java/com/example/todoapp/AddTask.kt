package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class AddTask : DialogFragment() {

    interface OnDialogResultListener {
        fun onDialogResult(taskName: String, tag: String)
    }

    private var listener: OnDialogResultListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment, container, false)

        val etTaskName = view.findViewById<EditText>(R.id.etTaskName)
        val rbTagGroup = view.findViewById<RadioGroup>(R.id.rbTagGroup)


        view.findViewById<Button>(R.id.btnOk).setOnClickListener {
            val taskName = etTaskName.text.toString()
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

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener{
            dismiss()
        }

        return view
    }


    // Método para establecer el listener
    fun setOnDialogResultListener(listener: OnDialogResultListener) {
        this.listener = listener
    }
}
