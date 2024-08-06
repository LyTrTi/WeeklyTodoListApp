package com.example.weeklytodolist.data

import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTask(): Flow<List<Task>>
}