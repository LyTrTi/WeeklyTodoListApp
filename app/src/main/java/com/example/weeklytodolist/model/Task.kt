package com.example.weeklytodolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weeklytodolist.model.utils.DateFormatInfo
import com.example.weeklytodolist.model.utils.DateTimeConverter


@Entity(tableName = "task")
@TypeConverters(DateTimeConverter::class)
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String  = "",
    var done: Boolean = false,
    val archive: Boolean = false,
    val description: String = "",
    val dateTime: String = DateFormatInfo.currentDateTime(),
)