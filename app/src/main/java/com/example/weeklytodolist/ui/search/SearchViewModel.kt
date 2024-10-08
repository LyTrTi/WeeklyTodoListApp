package com.example.weeklytodolist.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.domain.TaskRepository
import com.example.weeklytodolist.data.userPreferences.UserPreferencesRepository
import com.example.weeklytodolist.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

enum class TypeList {
    Suggestions, Result, Recent
}

data class SearchState(
    var type: TypeList = TypeList.Recent,
    var toShow: List<Any> = listOf(),
    var searchValue: String = ""
)

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    @Named("firestoreTaskRepository") private val taskRepository: TaskRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private var _recentSearches: StateFlow<List<String>> =
        userPreferencesRepository.recentSearches.map {
            it.toList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )
    var searchUiState by mutableStateOf(SearchState())
        private set

    init {
        viewModelScope.launch {
            _recentSearches.collectLatest {
                if (searchUiState.toShow.isEmpty()) {
                    searchUiState = searchUiState.copy(
                        toShow = it
                    )
                }
            }
        }
    }

    fun onQueryChange(text: String) {
        searchUiState = searchUiState.copy(
            searchValue = text
        )
        Log.d("SearchValue:", searchUiState.searchValue)
        runBlocking {
            delay(5L)
        }
    }

    fun onClearing() {
        searchUiState = searchUiState.copy(
            type = TypeList.Recent,
            toShow = _recentSearches.value,
            searchValue = ""
        )
    }

    fun onSearch() {
        viewModelScope.launch {
            searchUiState = searchUiState.copy(
                type = TypeList.Result,
                toShow = taskRepository.searchByName(searchUiState.searchValue)
            )
            userPreferencesRepository.addRecentSearch(searchUiState.searchValue)
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

    fun clearHistory() {
        viewModelScope.launch {
            userPreferencesRepository.clearSearches()
        }
    }
}

fun SearchState.asListTask(): List<Task> {
    return this.toShow.map {
        it as Task
    }
}

fun SearchState.asListString(): List<String> {
    return this.toShow.map {
        it as String
    }
}