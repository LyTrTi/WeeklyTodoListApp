package com.example.weeklytodolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weeklytodolist.TaskApplication
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    private val scope = viewModelScope


    init {
        initializeTasks()
    }

    fun updateTaskState(task: Task) {
        //NOTE: this task argument was already changed the attributes.
        //TODO: Also update achieved task: done == true && achieve == true
        scope.launch { repository.modifyTable(task) }
    }

//    fun getTaskById(taskId: Int) : Task {
//        return repository.getTaskById(taskId)
//    }


    private fun initializeTasks() {
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TaskApplication
                TaskViewModel(application.appContainer.taskRepository)
            }
        }
    }
}

