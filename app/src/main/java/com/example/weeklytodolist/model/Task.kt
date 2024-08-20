package com.example.weeklytodolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String  = "",
    var done: Boolean = false,
    val archive: Boolean = false,
    val description: String = "",
)