package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), DialogFragment.OnDialogResultListener {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleButtons)

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val selectedTags = mutableListOf<String>()

            // Iterate over all toggle buttons
            for (buttonId in toggleGroup.checkedButtonIds) {
                // Map the checked button ID to its corresponding tag
                val tag = when (buttonId) {
                    R.id.workToggle -> "Work"
                    R.id.personalToggle -> "Personal"
                    R.id.fitnessToggle -> "Fitness"
                    else -> ""
                }
                // Add the tag to the selectedTags list
                selectedTags.add(tag)
            }

            // Filter tasks based on selected tags
            taskViewModel.filtrarPorEtiquetas(selectedTags)
        }



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskAdapter(emptyList(), onDeleteClickListener = { task ->
            taskViewModel.removeTask(task)
        }, onCheckedChangeListener = { taskId, completed ->
            taskViewModel.markTaskCompleted(taskId, completed)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.filteredTasks.observe(this, Observer { tasks ->
            adapter.updateTasks(tasks)
        })


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val dialog = DialogFragment()
            dialog.setOnDialogResultListener(this)
            dialog.show(supportFragmentManager, "AddTaskDialog")
        }

    }

    override fun onDialogResult(taskName: String, tag: String) {
        val newTask = Task(
            id = (taskViewModel.tasks.value?.size ?: 0) + 1,
            title = taskName,
            tag = tag,
            completed = false
        )
        taskViewModel.agregarTarea(newTask)
    }

}
