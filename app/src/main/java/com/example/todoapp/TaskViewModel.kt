package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    private val _filteredTasks = MutableLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> get() = _filteredTasks

    private val _completedTasks = mutableMapOf<Int, Boolean>()
    private var allTasks: List<Task> = emptyList()

    init {
        val initialTasks = listOf<Task>()
        _tasks.value = initialTasks
        _filteredTasks.value = initialTasks
    }

    fun agregarTarea(tarea: Task) {
        val updatedTasks = (_tasks.value ?: emptyList()) + tarea
        _tasks.value = updatedTasks
        _filteredTasks.value = updatedTasks
        allTasks = updatedTasks
        _completedTasks[tarea.id] = tarea.completed
    }

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


    fun removeTask(task: Task) {
        val updatedTasks = _tasks.value.orEmpty() - task
        _tasks.value = updatedTasks
        _filteredTasks.value = updatedTasks
        allTasks = updatedTasks
        _completedTasks.remove(task.id)
    }


    fun removeAllTasks() {
        val emptyList = emptyList<Task>()
        _tasks.value = emptyList
        _filteredTasks.value = emptyList
        allTasks = emptyList
        _completedTasks.clear()
    }


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
        _filteredTasks.value = updatedTasks  // Update the filtered list as well
        allTasks = updatedTasks
    }

}
