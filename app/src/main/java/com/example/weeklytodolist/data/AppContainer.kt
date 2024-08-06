package com.example.weeklytodolist.data

import android.content.Context

interface AppContainer {
    val taskRepository: TaskRepository
}

class DefaultAppContainer(context: Context): AppContainer {
    override val taskRepository by lazy {
        OfflineTaskRepository(TaskDatabase.getDatabase(context).getDao())
    }
}