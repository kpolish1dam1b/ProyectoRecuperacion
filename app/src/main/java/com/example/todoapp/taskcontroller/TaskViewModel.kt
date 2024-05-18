package com.example.todoapp.taskcontroller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.Task

// ViewModel para manejar la lógica relacionada con las tareas
class TaskViewModel : ViewModel() {
    // LiveData mutable para la lista de todas las tareas
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks // LiveData inmutable para exponer la lista de tareas

    // LiveData mutable para la lista de tareas filtradas
    private val _filteredTasks = MutableLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> get() = _filteredTasks // LiveData inmutable para exponer la lista de tareas filtradas

    // Mapa mutable para almacenar el estado completado de cada tarea
    private val _completedTasks = mutableMapOf<Int, Boolean>()

    // Lista de todas las tareas
    private var allTasks: List<Task> = emptyList()

    // Inicialización del ViewModel
    init {
        val initialTasks = listOf<Task>()
        _tasks.value = initialTasks
        _filteredTasks.value = initialTasks
    }

    // Método para agregar una nueva tarea
    fun agregarTarea(tarea: Task) {
        val updatedTasks = (_tasks.value ?: emptyList()) + tarea
        _tasks.value = updatedTasks
        _filteredTasks.value = updatedTasks
        allTasks = updatedTasks
        _completedTasks[tarea.id] = tarea.completed
    }

    // Método para filtrar las tareas por etiquetas
    fun filtrarPorEtiquetas(etiquetas: List<String>) {
        val filtered = if (etiquetas.isEmpty()) {
            allTasks
        } else {
            allTasks.filter { task ->
                etiquetas.any { etiqueta ->
                    task.tag == etiqueta
                }
            }
        }
        _filteredTasks.value = filtered
    }

    // Método para eliminar una tarea
    fun removeTask(task: Task) {
        val updatedTasks = _tasks.value.orEmpty() - task
        _tasks.value = updatedTasks
        _filteredTasks.value = updatedTasks
        allTasks = updatedTasks
        _completedTasks.remove(task.id)
    }

    // Método para eliminar todas las tareas
    fun removeAllTasks() {
        val emptyList = emptyList<Task>()
        _tasks.value = emptyList
        _filteredTasks.value = emptyList
        allTasks = emptyList
        _completedTasks.clear()
    }

    // Método para marcar una tarea como completada o no completada
    fun markTaskCompleted(taskId: Int, completed: Boolean) {
        _completedTasks[taskId] = completed
        val updatedTasks = _tasks.value?.map { task ->
            if (task.id == taskId) {
                task.copy(completed = completed)
            } else {
                task
            }
        } ?: emptyList()
        _tasks.value = updatedTasks
        _filteredTasks.value = updatedTasks  // Actualizar la lista filtrada también
        allTasks = updatedTasks
    }
}
