package com.example.weeklytodolist.data

import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val dao: TaskDao): TaskRepository {
    override fun getAllTask(): Flow<List<Task>> {
        return dao.getAllTask()
    }
}