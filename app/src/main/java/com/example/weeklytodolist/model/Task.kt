package com.example.weeklytodolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weeklytodolist.ui.task.TaskDetails


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var done: Boolean = false,
    val archive: Boolean = false,
    val description: String,
)

fun Task.toDetails(): TaskDetails {
    return TaskDetails(name = name, description = description)
}