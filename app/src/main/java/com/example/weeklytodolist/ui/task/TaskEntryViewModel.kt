package com.example.weeklytodolist.ui.task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.launch

class TaskEntryViewModel(private val taskRepository: TaskRepository): ViewModel() {
    var taskInfo by mutableStateOf(TaskDetails())

    fun insertTask() {
        val task = taskInfo.toTask()

        viewModelScope.launch { taskRepository.modifyTable(task) }
        resetTask()
    }

    private fun resetTask() {
        taskInfo = taskInfo.copy(name = "", description = "")
    }
}

data class TaskDetails(
    var name: String = "",
    var description: String = ""
)

fun TaskDetails.toTask(): Task {
    return Task(name = name, description = description)
}
