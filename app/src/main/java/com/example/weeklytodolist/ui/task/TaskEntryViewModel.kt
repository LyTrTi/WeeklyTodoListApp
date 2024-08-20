package com.example.weeklytodolist.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.launch

class TaskEntryViewModel(private val taskRepository: TaskRepository): ViewModel() {
    var newTask by mutableStateOf(Task())

    fun insertTask() {
        viewModelScope.launch { taskRepository.modifyTable(newTask) }
        resetTask()
    }

    private fun resetTask() {
        newTask = newTask.copy(name = "", description = "")
    }
}
