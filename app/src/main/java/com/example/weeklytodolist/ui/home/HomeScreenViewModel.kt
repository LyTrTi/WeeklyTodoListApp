package com.example.weeklytodolist.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.domain.TaskRepository
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.model.utils.DateFormatInfo
import com.example.weeklytodolist.model.utils.getDayOfWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class HomeScreenViewModel @Inject constructor (
    @Named("firestoreTaskRepository") private val taskRepository: TaskRepository
) : ViewModel() {
    private val homeUiState: StateFlow<HomeUiState> =
        taskRepository.getAllTaskFlow()
            .catch {
                if (it is IOException) {
                    Log.d(TAG, "Failed to get Task of database", it)
                } else {
                    HomeUiState(status = Status.ERROR, message = it.message.toString())
                }
            }
            .map {
                Log.d("DEBUG: List tasks", it.toString())
                HomeUiState(
                    status = Status.SUCCESS,
                    tasks = it,
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    var taskListState by mutableStateOf(TaskListState())

    init {
        viewModelScope.launch {
            homeUiState.collectLatest {
                taskListState = TaskListState(
                    tabList = when (taskListState.tab) {
                        TypeList.DEFAULT -> it.tasks.filter { task -> !(task.done || task.archive) }
                        TypeList.DONE -> it.tasks.filter { task -> task.done }
                        TypeList.ARCHIVE -> it.tasks.filter { task -> task.done && task.archive }
                    },
                    tab = taskListState.tab,
                    dayOfWeek = taskListState.dayOfWeek
                )
                tabChanged(taskListState.tab.name)
            }
        }
    }

    fun tabChanged(tabTitle: String) {
        Log.d("DEBUG: Tab's title", tabTitle)
        taskListState = when (tabTitle) {
            TypeList.DONE.name -> taskListState.copy(
                tabList = homeUiState.value.tasks.filter { task -> task.done },
                tab = TypeList.DONE
            )

            TypeList.ARCHIVE.name -> taskListState.copy(
                tabList = homeUiState.value.tasks.filter { task -> task.done && task.archive },
                tab = TypeList.ARCHIVE
            )

            TypeList.DEFAULT.name -> taskListState.copy(
                tabList = homeUiState.value.tasks.filter { task -> !(task.done || task.archive) },
                tab = TypeList.DEFAULT
            )

            else -> taskListState.copy(
                tabList = homeUiState.value.tasks.filter { task -> !(task.done || task.archive) },
                tab = TypeList.DEFAULT
            )
        }
        Log.d(
            "DEBUG: Tab changed",
            taskListState.tabList.toString()
        )
        dayChanged(taskListState.dayOfWeek)
    }

    fun dayChanged(day: String) {
        Log.d("DateList: day parameter", day)
        taskListState = taskListState.copy(
            currentList = taskListState.tabList.filter {
                day.equals(it.getDayOfWeek(), ignoreCase = true)
            },
            dayOfWeek = day
        )
    }

    fun markAsDone(task: Task) {
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

enum class TypeList {
    DEFAULT, ARCHIVE, DONE
}

data class TaskListState(
    var tabList: List<Task> = listOf(),
    var currentList: List<Task> = listOf(),
    var tab: TypeList = TypeList.DEFAULT,
    var dayOfWeek: String = DateFormatInfo.currentDayOfWeek(),
)