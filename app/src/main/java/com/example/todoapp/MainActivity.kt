package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.taskcontroller.TaskAdapter
import com.example.todoapp.taskcontroller.TaskViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Clase principal de la aplicación que muestra la lista de tareas
class MainActivity : AppCompatActivity(), DialogFragment.OnDialogResultListener {

    // ViewModel para manejar la lógica de la aplicación y los datos
    private lateinit var taskViewModel: TaskViewModel
    // Adaptador para el RecyclerView
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración del botón de menú
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener { view ->
            showPopupMenu(view)
        }

        // Inicialización del ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Configuración del grupo de botones de alternancia
        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleButtons)
        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val selectedTags = mutableListOf<String>()

            // Itera sobre todos los botones de alternancia seleccionados
            for (buttonId in toggleGroup.checkedButtonIds) {
                // Mapea el ID del botón seleccionado a su correspondiente etiqueta
                val tag = when (buttonId) {
                    R.id.workToggle -> "Work"
                    R.id.personalToggle -> "Personal"
                    R.id.fitnessToggle -> "Fitness"
                    else -> ""
                }
                // Agrega la etiqueta a la lista de etiquetas seleccionadas
                selectedTags.add(tag)
            }

            // Filtra las tareas en el ViewModel basándose en las etiquetas seleccionadas
            taskViewModel.filtrarPorEtiquetas(selectedTags)
        }

        // Configuración del RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskAdapter(emptyList(), onDeleteClickListener = { task ->
            // Maneja la eliminación de una tarea
            taskViewModel.removeTask(task)
        }, onCheckedChangeListener = { taskId, completed ->
            // Maneja el cambio de estado de una tarea (completada o no)
            taskViewModel.markTaskCompleted(taskId, completed)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observa los cambios en las tareas filtradas y actualiza el adaptador
        taskViewModel.filteredTasks.observe(this, Observer { tasks ->
            adapter.updateTasks(tasks)
        })

        // Configuración del botón de acción flotante
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            // Muestra un diálogo para agregar una nueva tarea
            val dialog = DialogFragment()
            dialog.setOnDialogResultListener(this)
            dialog.show(supportFragmentManager, "AddTaskDialog")
        }
    }

    // Maneja el resultado del diálogo de agregar tarea
    override fun onDialogResult(taskName: String, tag: String) {
        // Crea una nueva tarea con los datos del diálogo
        val newTask = Task(
            id = (taskViewModel.tasks.value?.size ?: 0) + 1,
            title = taskName,
            tag = tag,
            completed = false
        )
        // Agrega la nueva tarea al ViewModel
        taskViewModel.agregarTarea(newTask)
    }

    // Muestra un menú emergente (popup menu) cuando se hace clic en el botón de menú
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        // Infla el menú desde el recurso de menú
        inflater.inflate(R.menu.popup_menu, popup.menu)
        // Define el comportamiento de los ítems del menú
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    // Navega a LoginActivity y finaliza la actividad actual
                    startActivity(Intent(this, LoginActivity::class.java))
                    this.finish()
                    true
                }
                R.id.menu_item2 -> {
                    // Elimina todas las tareas y muestra un mensaje
                    taskViewModel.removeAllTasks()
                    Toast.makeText(this, "All tasks erased", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        // Muestra el menú emergente
        popup.show()
    }
}
