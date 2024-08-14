package com.example.weeklytodolist.ui

import android.app.Application
import android.icu.util.Calendar.WeekData
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weeklytodolist.TaskApplication
import com.example.weeklytodolist.ui.home.ContentViewModel
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.task.TaskEntryViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(weeklyTodosApplication().appContainer.taskRepository)
        }

        initializer {
            ContentViewModel(weeklyTodosApplication().appContainer.taskRepository)
        }

        initializer {
            TaskEntryViewModel(weeklyTodosApplication().appContainer.taskRepository)
        }
    }
}

fun CreationExtras.weeklyTodosApplication(): TaskApplication {
    return this[APPLICATION_KEY] as TaskApplication
}