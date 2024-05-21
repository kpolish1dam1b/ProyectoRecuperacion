package com.example.todoapp.taskcontroller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.database.AppDatabase
import com.example.todoapp.model.entity.Task
import com.example.todoapp.model.dao.TaskDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao: TaskDAO
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks
    private val _filteredTasks = MutableLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> get() = _filteredTasks
    private val _favouriteTasks = MutableLiveData<List<Task>>()
    val favouriteTasks: LiveData<List<Task>> get() = _favouriteTasks
    private val _completedTasks = mutableMapOf<Int, Boolean>()

    init {
        val database = AppDatabase.getDatabase(application)
        taskDao = database.taskDao()
        refreshTasks()
    }

    fun agregarTarea(tarea: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(tarea)
            refreshTasks()
        }
    }

    fun filtrarPorEtiquetas(etiquetas: List<String>) {
        val filtered = if (etiquetas.isEmpty()) {
            _tasks.value ?: emptyList()
        } else {
            _tasks.value?.filter { task ->
                etiquetas.any { etiqueta ->
                    task.tag == etiqueta
                }
            } ?: emptyList()
        }
        _filteredTasks.postValue(filtered)
    }

    fun removeTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
            withContext(Dispatchers.Main) {
                val updatedTasks = _tasks.value.orEmpty() - task
                _tasks.value = updatedTasks
                _filteredTasks.value = updatedTasks
                _completedTasks.remove(task.id)
                if (task.favourite) {
                    _favouriteTasks.value = _favouriteTasks.value.orEmpty() - task
                }
            }
        }
    }

    fun removeAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteAll()
            withContext(Dispatchers.Main) {
                val emptyList = emptyList<Task>()
                _tasks.value = emptyList
                _filteredTasks.value = emptyList
                _completedTasks.clear()
                _favouriteTasks.value = emptyList
            }
        }
    }

    fun markTaskCompleted(taskId: Int, completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskToUpdate = _tasks.value?.find { it.id == taskId }?.copy(completed = completed)
            taskToUpdate?.let {
                taskDao.update(it)
                withContext(Dispatchers.Main) {
                    _completedTasks[taskId] = completed
                    val updatedTasks = _tasks.value?.map { task ->
                        if (task.id == taskId) {
                            it
                        } else {
                            task
                        }
                    } ?: emptyList()
                    _tasks.value = updatedTasks
                    _filteredTasks.value = updatedTasks
                }
            }
        }
    }

    fun addToFavourites(taskId: Int, favourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskToUpdate = _tasks.value?.find { it.id == taskId }?.copy(favourite = favourite)
            taskToUpdate?.let {
                taskDao.update(it)
                withContext(Dispatchers.Main) {
                    val updatedTasks = _tasks.value?.map { task ->
                        if (task.id == taskId) {
                            it
                        } else {
                            task
                        }
                    } ?: emptyList()
                    _tasks.value = updatedTasks
                    _filteredTasks.value = updatedTasks

                    if (favourite) {
                        _favouriteTasks.value = (_favouriteTasks.value ?: emptyList()) + it
                    } else {
                        _favouriteTasks.value = _favouriteTasks.value?.filter { task -> task.id != taskId }
                    }
                }
            }
        }
    }

    private fun refreshTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val allTasks = taskDao.getAll()
            withContext(Dispatchers.Main) {
                _tasks.value = allTasks
                _filteredTasks.value = allTasks
            }
        }
    }

    fun getFavoriteTasks(): LiveData<List<Task>> {
        return taskDao.getFavouriteTasks()
    }
}
