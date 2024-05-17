package com.example.todoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: List<Task>,
    private val onDeleteClickListener: (Task) -> Unit,
    private val onCheckedChangeListener: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.check)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val tag: TextView = itemView.findViewById(R.id.tvTag)
        val deleteButton: ImageButton = itemView.findViewById(R.id.ibDelete)

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    onCheckedChangeListener(task.id, isChecked)
                    updateTitleStrikeThrough(isChecked)
                }
            }

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    onDeleteClickListener(task)
                }
            }
        }

        private fun updateTitleStrikeThrough(completed: Boolean) {
            val flags = if (completed) {
                Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                0
            }
            title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv() or flags
        }

        fun bind(task: Task) {
            title.text = task.title
            tag.text = task.tag
            checkBox.isChecked = task.completed
            updateTitleStrikeThrough(task.completed)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount() = tasks.size
}




