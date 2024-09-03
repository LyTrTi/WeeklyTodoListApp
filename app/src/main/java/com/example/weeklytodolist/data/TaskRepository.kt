package com.example.weeklytodolist.data

import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTaskFlow(): Flow<List<Task>>
    fun getTaskByIdFlow(taskId: Int): Flow<Task>
    suspend fun getAllTask(): List<Task>
    suspend fun getTaskById(taskId: Int): Task
    suspend fun searchByName(taskName: String): List<Task>
    suspend fun deleteTask(taskId: Int)
    suspend fun modifyTable(task: Task)
}