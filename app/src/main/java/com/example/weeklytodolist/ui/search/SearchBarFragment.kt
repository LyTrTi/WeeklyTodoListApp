package com.example.weeklytodolist.ui.search

import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.home.fragment.TaskListFragment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFragment(
    modifier: Modifier = Modifier,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
    onCardClicked: (Task) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchUiState by remember {
        derivedStateOf {
            searchScreenViewModel.searchUiState
        }
    }

    SearchBar(
        modifier = modifier.onFocusChanged {
            if (!it.isFocused) {
                InputMethodManager.HIDE_IMPLICIT_ONLY
            }
        },
        inputField = {
            SearchBarDefaults.InputField(
                query = searchUiState.searchValue,
                onQueryChange = {
                    if (it.isNotEmpty()) {
                        searchScreenViewModel.onQueryChange(it)
                        searchScreenViewModel.onTyping()
                    } else searchScreenViewModel.onClearing()
                },
                onSearch = {
                    searchScreenViewModel.onSearch()
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                leadingIcon = {
                    IconButton(onClick = {
                        if (expanded) {
                            searchScreenViewModel.onClearing()
                        }
                        expanded = !expanded
                    }) {
                        Icon(
                            imageVector = when (expanded) {
                                true -> Icons.AutoMirrored.Filled.ArrowBack
                                else -> Icons.Default.Search
                            },
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (expanded) {
                        IconButton(onClick = { searchScreenViewModel.clearHistory() }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                },
                placeholder = { Text(text = stringResource(id = R.string.search_task)) }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        Column {
            val type = searchUiState.type
            Text(
                text = type.name,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            when (type) {
                TypeList.Recent ->
                    ListRecentSearchesFragment(
                        listOptions = if (searchUiState.toShow.isEmpty()) emptyList<String>() else searchUiState.asListString(),
                    ) { recentSearchValue ->
                        keyboardController?.hide()
                        searchScreenViewModel.onQueryChange(recentSearchValue)
                        searchScreenViewModel.onTyping()
                    }

                TypeList.Suggestions ->
                    ListSuggestionsFragment(
                        listOptions = if (searchUiState.toShow.isEmpty()) emptyList<Task>() else searchUiState.asListTask(),
                        searchValue = searchUiState.searchValue,
                        onOptionClicked = {
                            keyboardController?.hide()
                            searchScreenViewModel.onQueryChange(it)
                            searchScreenViewModel.onSearch()
                        }
                    )

                else -> {
                    TaskListFragment(
                        listTasks = if (searchUiState.toShow.isEmpty()) emptyList<Task>() else searchUiState.asListTask(),
                        onDoneClicked = {
                            //Do Nothing
                        }
                    ) {
                        onCardClicked(it)
                    }
                }
            }
        }
    }
}