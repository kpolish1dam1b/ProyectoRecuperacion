package com.example.todoapp.taskcontroller

data class Task(
    val id: Int ,
    val title: String,
    val tag: String,
    var completed: Boolean = false
)
