package com.example.todoapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "completed") var completed: Boolean = false,
    @ColumnInfo(name = "favourite") var favourite: Boolean = false
)
