package com.example.todoapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.entity.Task

@Dao
interface TaskDAO {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<Task>

    @Insert
    fun insert(vararg task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAll()

    @Update
    fun update(task: Task)
}