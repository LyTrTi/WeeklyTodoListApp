package com.example.weeklytodolist.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class HomeScreenViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        taskRepository.getAllTaskFlow().map {
            Log.d("DEBUG: List tasks", it.toString())
            HomeUiState(
                status = Status.SUCCESS,
                tasks = it,
            )
        }.catch {
            HomeUiState(status = Status.ERROR, message = it.message.toString())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
    var tabState by mutableStateOf(TabState())

    init {
        viewModelScope.launch {
            homeUiState.collectLatest {
                tabState = TabState(
                    currentList = when (tabState.tab) {
                        TypeList.DEFAULT -> it.tasks.filter { task -> !(task.done || task.achieve) }
                        TypeList.DONE -> it.tasks.filter { task -> task.done }
                        TypeList.ACHIEVE -> it.tasks.filter { task -> task.done && task.achieve }
                    },
                    tab = tabState.tab
                )
                Log.d("DEBUG: init viewmodel", tabState.tab.name)
            }
        }
    }

    fun tabChanged(tabTitle: String = TypeList.DEFAULT.name) {
        Log.d("Tab's title", tabTitle)
        tabState = when (tabTitle) {
            TypeList.DONE.name -> tabState.copy(
                currentList = homeUiState.value.tasks.filter { task -> task.done },
                tab = TypeList.DONE
            )

            TypeList.ACHIEVE.name -> tabState.copy(
                currentList = homeUiState.value.tasks.filter { task -> task.done && task.achieve },
                tab = TypeList.ACHIEVE
            )

            TypeList.DEFAULT.name -> tabState.copy(
                currentList = homeUiState.value.tasks.filter { task -> !(task.done || task.achieve) },
                tab = TypeList.DEFAULT
            )

            else -> tabState.copy(
                currentList = homeUiState.value.tasks.filter { task -> !(task.done || task.achieve) },
                tab = TypeList.DEFAULT
            )
        }
        Log.d(
            "DEBUG: current list",
            tabState.currentList.toString()
        )
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch { taskRepository.deleteTask(taskId) }
    }

    fun markAsDone(task: Task) {
        Log.d("DEBUG: MarkAsDone", tabState.tab.name)
        viewModelScope.launch {
            taskRepository.modifyTable(task.copy(done = !task.done))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

enum class Status {
    LOADING, SUCCESS, ERROR
}

data class HomeUiState(
    var status: Status = Status.LOADING,
    var message: String = "",
    val tasks: List<Task> = listOf(),
)

data class TabState(
    var currentList: List<Task> = listOf(),
    var tab: TypeList = TypeList.DEFAULT
)

enum class TypeList {
    DEFAULT, ACHIEVE, DONE
}