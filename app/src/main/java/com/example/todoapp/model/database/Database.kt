package com.example.todoapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.model.dao.TaskDAO
import com.example.todoapp.model.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO

    companion object{
        private const val DATABASE_NAME = "PMDM.db"

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    // Abrir conexión
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
                    INSTANCE = instance
                }

                return instance
            }
        }

    }
}