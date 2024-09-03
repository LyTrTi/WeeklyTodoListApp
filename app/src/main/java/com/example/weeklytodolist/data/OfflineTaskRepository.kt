package com.example.weeklytodolist.data

import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val dao: TaskDao): TaskRepository {
    override fun getAllTaskFlow(): Flow<List<Task>> {
        return dao.getAllTaskFlow()
    }

    override fun getTaskByIdFlow(taskId: Int): Flow<Task> {
        return dao.getTaskByIdFlow(taskId)
    }

    override suspend fun getAllTask(): List<Task> {
        return dao.getAllTask()
    }

    override suspend fun getTaskById(taskId: Int): Task {
        return dao.getTaskById(taskId)
    }

    override suspend fun searchByName(taskName: String): List<Task> {
        return dao.searchByName(taskName)
    }

    override suspend fun deleteTask(taskId: Int) {
        dao.deleteTask(taskId)
    }

    override suspend fun modifyTable(task: Task) {
        dao.modifyTable(task)
    }
}