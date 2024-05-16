package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), AddTask.OnDialogResultListener {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.tasks.observe(this, Observer { tasks ->
            adapter = TaskAdapter(tasks)
            recyclerView.adapter = adapter
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val dialog = AddTask()
            dialog.setOnDialogResultListener(this)
            dialog.show(supportFragmentManager, "AddTaskDialog")
        }

    }

    override fun onDialogResult(taskName: String, tag: String) {
        val newTask = Task(
            id = (taskViewModel.tasks.value?.size ?: 0) + 1,
            title = taskName,
            tag = tag
        )
        taskViewModel.agregarTarea(newTask)
    }

}
