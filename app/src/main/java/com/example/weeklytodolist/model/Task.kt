package com.example.weeklytodolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var done: Boolean = false,
    val achieve: Boolean = false,
    val description: String,
) {
    companion object {
        fun create(name: String = "", description: String = ""): Task {
            return Task(name = name, description = description)
        }
    }
}