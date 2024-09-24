package com.example.weeklytodolist.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.domain.TaskRepository
import com.example.weeklytodolist.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TaskEntryViewModel @Inject constructor(
    @Named("firestoreTaskRepository") private val taskRepository: TaskRepository
) : ViewModel() {
    var newTask by mutableStateOf(Task())

    fun insertTask() {
        viewModelScope.launch { taskRepository.modifyTable(newTask) }
        resetTask()
    }

    private fun resetTask() {
        newTask = newTask.copy(name = "", description = "")
    }
}
