package com.example.todoapp.taskcontroller

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.entity.Task

// Adaptador para gestionar la lista de tareas en un RecyclerView
class FavouritesAdapter(
    private var tasks: List<Task>, // Lista de tareas
    private val onDeleteClickListener: (Task) -> Unit, // Callback para manejar la eliminación de una tarea
    private val onCheckedChangeListener: (Int, Boolean) -> Unit, // Callback para manejar el cambio de estado de una tarea
    private val onFavouriteClickListener: (Task) -> Unit
) : RecyclerView.Adapter<FavouritesAdapter.TaskViewHolder>() {

    // Método para actualizar la lista de tareas
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
    }

    // Clase interna para el ViewHolder
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.check)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val tag: TextView = itemView.findViewById(R.id.tvTag)
        val favouriteButton: ImageButton = itemView.findViewById(R.id.ibFavs)
        val deleteButton: ImageButton = itemView.findViewById(R.id.ibDelete)

        init {
            // Listener para el cambio de estado del CheckBox
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    onCheckedChangeListener(task.id, isChecked)
                    updateTitleStrikeThrough(isChecked) // Actualizar el estilo del título
                }
            }

            // Listener para el botón de eliminar
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    onDeleteClickListener(task)
                }
            }

            // Listener para el botón de favoritos
            favouriteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    onFavouriteClickListener(task)
                    updateFavouriteIcon(task.favourite) // Actualizar el icono de favorito
                }
            }
        }

        // Método para actualizar el estilo del título (tachado si está completada)
        private fun updateTitleStrikeThrough(completed: Boolean) {
            val flags = if (completed) {
                Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                0
            }
            title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv() or flags
        }

        // Método para actualizar el icono de favorito
        private fun updateFavouriteIcon(isFavorite: Boolean) {
            val favIconColor = if (isFavorite) {
                ContextCompat.getColor(itemView.context, R.color.md_theme_onSecondaryContainer)
            } else {
                ContextCompat.getColor(itemView.context, com.google.android.material.R.color.m3_ref_palette_white)
            }
            favouriteButton.setColorFilter(favIconColor)
        }

        // Método para vincular una tarea a las vistas
        fun bind(task: Task) {
            title.text = task.title
            tag.text = task.tag
            checkBox.isChecked = task.completed
            updateTitleStrikeThrough(task.completed)
            updateFavouriteIcon(task.favourite)
        }
    }

    // Método para crear el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return TaskViewHolder(view)
    }

    // Método para vincular datos del ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task) // Vincular la tarea actual al ViewHolder
    }

    // Método para obtener el tamaño de la lista de tareas
    override fun getItemCount() = tasks.size
}
