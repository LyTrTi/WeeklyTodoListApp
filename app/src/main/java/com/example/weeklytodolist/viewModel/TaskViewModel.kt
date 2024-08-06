package com.example.weeklytodolist.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weeklytodolist.TaskApplication
import com.example.weeklytodolist.data.AppContainer
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.viewModel.ListTaskState.DefaultList
import com.example.weeklytodolist.viewModel.ListTaskState.DoneList
import com.example.weeklytodolist.viewModel.ListTaskState.AchieveList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TypeList {
    DEFAULT, DONE, ACHIEVE
}

interface ListTaskState {
    data class DefaultList(
        var listTask: List<Task> = emptyList(),
        val name: TypeList = TypeList.DEFAULT
    ) : ListTaskState

    data class DoneList(
        var listTask: List<Task> = emptyList(),
        val name: TypeList = TypeList.DONE
    ) : ListTaskState

    data class AchieveList(
        var listTask: List<Task> = emptyList(),
        val name: TypeList = TypeList.ACHIEVE
    ) : ListTaskState
}

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    private lateinit var _tasks: StateFlow<DefaultList>
    private var _doneTasks by mutableStateOf(DoneList())
    private var _achievedTasks by mutableStateOf(AchieveList())

    lateinit var currentListTask: List<Task>
    private var taskInfo by mutableStateOf(Task.create())

    init {
        getTasks()
        updateCurrentListTask(TypeList.DEFAULT.name)
        Log.d("DEBUG: Default Task", _tasks.value.listTask.toString())
    }

    fun updateCurrentListTask(type: String) {
        currentListTask = when (type) {
            TypeList.DEFAULT.name -> _tasks.value.listTask
            TypeList.DONE.name -> _doneTasks.listTask
            TypeList.ACHIEVE.name -> _achievedTasks.listTask
            else -> _tasks.value.listTask
        }
    }

    fun insertTask(name: String, description: String) {
        taskInfo = taskInfo.copy(
            name = name,
            description = description
        )
        _tasks.value.listTask += taskInfo
    }

    fun updateTaskState(task: Task) {
        //TODO: Also update achieved task: done == true && achieve == true
        _doneTasks.listTask.find { it == task }?.done = !task.done
    }

    private fun getTasks() {
        viewModelScope.launch {
            _tasks = repository.getAllTask().map { listTask ->
                DefaultList(listTask)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = DefaultList(emptyList())
            ).also {
                _doneTasks =
                    DoneList((it.value.listTask.filter { task -> task.done }))
                _achievedTasks =
                    AchieveList(it.value.listTask.filter { task -> task.achieve })
            }
        }
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
