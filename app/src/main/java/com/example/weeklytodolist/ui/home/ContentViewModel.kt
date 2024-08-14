package com.example.weeklytodolist.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.launch

class ContentViewModel(private val taskRepository: TaskRepository) : ViewModel() {
//
//
//    fun deleteTask(taskId: Int) {
//        viewModelScope.launch { taskRepository.deleteTask(taskId) }
//    }
//
//    fun tabChanged(tabTitle: String) {
//        Log.d("Tab's title", tabTitle)
//        tabState = when (tabTitle) {
//            TypeList.DONE.name -> tabState
//                .copy(
//                    tabTasks = homeUiState.value.taskList.filter { it.done },
//                    type = TypeList.DONE
//                )
//
//            TypeList.ACHIEVE.name -> tabState
//                .copy(
//                    tabTasks = homeUiState.value.taskList.filter { (it.done && it.achieve) },
//                    type = TypeList.ACHIEVE
//                )
//
//            else -> tabState
//                .copy(
//                    tabTasks = homeUiState.value.taskList.filter { !(it.done && it.achieve) },
//                    type = TypeList.DEFAULT
//                )
//        }
//        Log.d("DEBUG: ${tabState.type}'s tasks", tabState.tabTasks.toString())
//    }
//
//    fun markAsDone(task: Task) {
//        viewModelScope.launch {
//            taskRepository.modifyTable(task.copy(done = !task.done))
//        }
//    }
}
