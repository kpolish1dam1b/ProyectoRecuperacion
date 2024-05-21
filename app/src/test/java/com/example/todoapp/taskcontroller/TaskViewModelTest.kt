package com.example.todoapp.taskcontroller

import org.junit.Assert.*
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.todoapp.model.entity.Task
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TaskViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun agregarTarea() {
        val viewModel = TaskViewModel()
        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.tasks.observeForever(observer)
        val task = Task(id = 1, title = "Test Task", tag = "Work")
        viewModel.agregarTarea(task)
        verify(observer).onChanged(listOf(task))
    }

    @Test
    fun filtrarPorEtiquetas() {
        val viewModel = TaskViewModel()
        val task1 = Task(id = 1, title = "Work Task", tag = "Work")
        val task2 = Task(id = 2, title = "Personal Task", tag = "Personal")
        viewModel.agregarTarea(task1)
        viewModel.agregarTarea(task2)

        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.filteredTasks.observeForever(observer)
        viewModel.filtrarPorEtiquetas(listOf("Work"))
        verify(observer).onChanged(listOf(task1))
    }

    @Test
    fun removeTask() {
        val viewModel = TaskViewModel()
        val task = Task(id = 1, title = "Test Task", tag = "Work")
        viewModel.agregarTarea(task)

        val observer = Observer<List<Task>> {}
        viewModel.tasks.observeForever(observer)
        viewModel.removeTask(task)
        assertEquals(emptyList<Task>(), viewModel.tasks.value)
    }

    @Test
    fun removeAllTasks() {
        val viewModel = TaskViewModel()

        val task1 = Task(1, "Task 1", "Work", false)
        val task2 = Task(2, "Task 2", "Personal", false)
        viewModel.agregarTarea(task1)
        viewModel.agregarTarea(task2)

        val observer = Observer<List<Task>> {}
        viewModel.tasks.observeForever(observer)
        viewModel.removeAllTasks()
        assertEquals(emptyList<Task>(), viewModel.tasks.value)
    }

    @Test
    fun markTaskCompleted() {
        val viewModel = TaskViewModel()

        val task = Task(id = 1, title = "Test Task", tag = "Work")
        viewModel.agregarTarea(task)

        val observer = mock(Observer::class.java) as Observer<List<Task>>
        viewModel.tasks.observeForever(observer)

        viewModel.markTaskCompleted(task.id, true)

        verify(observer).onChanged(listOf(task.copy(completed = true)))
    }
}