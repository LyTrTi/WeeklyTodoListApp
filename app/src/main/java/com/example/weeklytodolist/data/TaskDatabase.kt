package com.example.weeklytodolist.data

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weeklytodolist.model.Task

@Database(
    entities = [Task::class],
    version = 2,
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        instance = it
                        Log.d("DEBUG: TASK DB", instance.toString())
                    }
            }
        }
    }
}