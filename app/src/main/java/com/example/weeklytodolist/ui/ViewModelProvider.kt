package com.example.weeklytodolist.ui

import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weeklytodolist.TaskApplication
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.search.SearchScreenViewModel
import com.example.weeklytodolist.ui.task.TaskDetailViewModel
import com.example.weeklytodolist.ui.task.TaskEntryViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(weeklyTodosApplication().appContainer.taskRepository)
        }

        initializer {
            TaskEntryViewModel(weeklyTodosApplication().appContainer.taskRepository)
        }

        initializer {
            SearchScreenViewModel(
                taskRepository = weeklyTodosApplication().appContainer.taskRepository,
                userPreferencesRepository = weeklyTodosApplication().userPreferencesRepository
            )
        }

        initializer {
            TaskDetailViewModel(
                this.createSavedStateHandle(),
                weeklyTodosApplication().appContainer.taskRepository
            )
        }
    }
}

fun CreationExtras.weeklyTodosApplication(): TaskApplication {
    return this[APPLICATION_KEY] as TaskApplication
}