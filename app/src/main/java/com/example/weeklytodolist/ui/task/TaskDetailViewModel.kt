package com.example.weeklytodolist.ui.task

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.domain.TaskRepository
import com.example.weeklytodolist.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @Named("firestoreTaskRepository") private val taskRepository: TaskRepository
) : ViewModel() {

    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailDestination.TASK_ID_ARG])

    var uiState: StateFlow<TaskDetailUiState> =
        taskRepository.getTaskByIdFlow(taskId).map {
            TaskDetailUiState(task = it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TaskDetailUiState()
        ).also {
            Log.d("DEBUG:", "TaskDetailViewModel StateFlow")
        }
        private set

    fun onDoneClicked() {
        val currentTask = uiState.value.task!!
        if (!currentTask.archive) {
            onModify(currentTask.copy(done = !currentTask.done))
        }
    }

    fun onArchiveClicked() {
        val currentTask = uiState.value.task!!
        if (currentTask.done) {
            onModify(currentTask.copy(archive = !currentTask.archive))
        }
    }

    fun deleteTask() {
        viewModelScope.launch { taskRepository.deleteTask(taskId) }
    }

    private fun onModify(task: Task) {
        viewModelScope.launch {
            taskRepository.modifyTable(task)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TaskDetailUiState(
    val deleted: Boolean = false,
    val task: Task? = null
)

data class FABItem(
    val icon: ImageVector,
    val name: String,
    val enable: Boolean = true,
    val onClicked: () -> Unit
)
