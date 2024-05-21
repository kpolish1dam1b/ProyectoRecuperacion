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
import com.example.todoapp.taskcontroller.FavouritesAdapter
import com.example.todoapp.taskcontroller.TaskViewModel
import com.google.android.material.button.MaterialButtonToggleGroup

class FavoritosActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: FavouritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val backButton: ImageButton = findViewById(R.id.goBack)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val menuButton: ImageButton = findViewById(R.id.menuButton)
        menuButton.setOnClickListener { view ->
            showPopupMenu(view)
        }

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleButtons)
        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val selectedTags = mutableListOf<String>()

            for (buttonId in toggleGroup.checkedButtonIds) {
                val tag = when (buttonId) {
                    R.id.workToggle -> "Work"
                    R.id.personalToggle -> "Personal"
                    R.id.fitnessToggle -> "Fitness"
                    else -> ""
                }
                selectedTags.add(tag)
            }

            taskViewModel.filtrarPorEtiquetas(selectedTags)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = FavouritesAdapter(emptyList(), onDeleteClickListener = { task ->
            taskViewModel.removeTask(task)
        }, onCheckedChangeListener = { taskId, completed ->
            taskViewModel.markTaskCompleted(taskId, completed)
        }, onFavouriteClickListener = { task ->
            val newFavouriteState = !task.favourite
            taskViewModel.addToFavourites(task.id, newFavouriteState)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.favouriteTasks.observe(this, Observer { favouriteTasks ->
            adapter.updateTasks(favouriteTasks)
        })

    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                R.id.menu_item2 -> {
                    taskViewModel.removeAllTasks()
                    Toast.makeText(this, "All tasks erased", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
