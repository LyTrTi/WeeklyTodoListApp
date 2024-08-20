package com.example.weeklytodolist.ui.task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.model.toDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailDestination.TASK_ID_ARG])

    var uiState: StateFlow<TaskDetails> =
        taskRepository.getTaskByIdFlow(taskId).map {
            it.toDetails()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TaskDetails()
        )

    var modifiedTask by mutableStateOf(uiState.value)

    init {
        Log.d("DETAIL: Viewmodel", taskId.toString())
    }

    fun onModify() {
        viewModelScope.launch {
            taskRepository.modifyTable(modifiedTask.toTask())
        }
    }

    fun getTask() {
        taskRepository.getTaskByIdFlow(taskId)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class TaskDetails(
    var name: String = "",
    var description: String = ""
)

fun TaskDetails.toTask(): Task {
    return Task(name = name, description = description)
}
