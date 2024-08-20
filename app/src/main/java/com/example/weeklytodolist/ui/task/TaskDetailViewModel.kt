package com.example.weeklytodolist.ui.task

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailDestination.TASK_ID_ARG])

    var uiState: StateFlow<Task> =
        taskRepository.getTaskByIdFlow(taskId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = Task(name = "", description = "")
        )

    lateinit var modifiedTask: MutableStateFlow<Task>

    init {
        viewModelScope.launch {
            uiState.collectLatest {
                modifiedTask = MutableStateFlow(it)
            }
        }
    }

    fun unDone() {
        Log.d("unDone: ", modifiedTask.value.toString())
        modifiedTask.update {
            it.copy(
                done = !it.done
            )
        }
        Log.d("unDone: ", modifiedTask.value.toString())
        onModify()
    }

    fun unDoneArchive() {
        modifiedTask.update {
            it.copy(
                archive = !it.archive
            )
        }
        onModify()
    }

    fun modifyName(newName: String) {
        modifiedTask.update {
            it.copy(
                name = newName
            )
        }
        onModify()
    }

    fun modifyDescription(newDesc: String) {
        modifiedTask.update {
            it.copy(
                description = newDesc
            )
        }
        onModify()
    }

    private fun onModify() {
        viewModelScope.launch {
            taskRepository.modifyTable(modifiedTask.value)
        }
    }

    fun getTask() {
        taskRepository.getTaskByIdFlow(taskId)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

//
//data class TaskDetails(
//    var name: String = "",
//    var description: String = ""
//)
//
//fun TaskDetails.toTask(): Task {
//    return Task(name = name, description = description)
//}
