package com.example.weeklytodolist.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.TaskRepository
import com.example.weeklytodolist.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class TypeList {
    Suggestions, Result, Recent
}

data class SearchState(
    var type: TypeList = TypeList.Recent,
    var toShow: List<Task> = listOf(),
    var searchValue: String = ""
)

//TODO: TypeList Recent: take it from data store -> String
//TODO: TypeList Suggestion: take it from database with search value -> String
//TODO: TypeList Result: take it from database -> task

class SearchResultViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    var searchUiState by mutableStateOf(SearchState())
        private set

    fun onQueryChange(text: String) {
        searchUiState = searchUiState.copy(
            searchValue = text
        )
        Log.d("SearchValue:", searchUiState.searchValue)
        runBlocking {
            delay(5L)
        }
        onTyping()
    }

    fun onClearing() {
        //TODO: DataStore -> toShow recent searches
        viewModelScope.launch {
            searchUiState = searchUiState.copy(
                type = TypeList.Recent,
                toShow = listOf(),
                searchValue = ""
            )
        }
    }

    fun onSearch() {
        viewModelScope.launch {
            searchUiState = searchUiState.copy(
                type = TypeList.Result,
                toShow = taskRepository.searchByName(searchUiState.searchValue)
            )
        }
    }

    fun onTyping() {
        viewModelScope.launch {
            searchUiState = searchUiState.copy(
                type = TypeList.Suggestions,
                toShow = taskRepository.searchByName(searchUiState.searchValue)
            )
            Log.d("Typing", searchUiState.searchValue)
            Log.d("Typing", "DB: ${taskRepository.searchByName(searchUiState.searchValue)}")
        }
    }
}
