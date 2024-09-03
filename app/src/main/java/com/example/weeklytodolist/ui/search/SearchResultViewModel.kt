package com.example.weeklytodolist.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchResultViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    lateinit var resultTasks: MutableList<Task>
        private set

    private var _searchValue = mutableStateOf("")
    val searchValue by _searchValue

    private var _isFocused by mutableStateOf(false)
    val isFocused = _isFocused

    fun onQueryChange(text: String) {
        _searchValue.value += text
        Log.d("SearchVM:", _searchValue.value)
    }

    fun onFocus() {
        _isFocused = !_isFocused
    }

    fun search() {
        runBlocking {
            resultTasks = taskRepository.searchByName(_searchValue.value).toMutableList()
        }
    }
}