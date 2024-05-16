package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        _tasks.value = listOf()
    }

    fun agregarTarea(tarea: Task) {
        _tasks.value = _tasks.value?.plus(tarea)
    }

    fun filtrarPorEtiqueta(etiqueta: String) {
        _tasks.value = _tasks.value?.filter { it.tag == etiqueta }
    }

}
