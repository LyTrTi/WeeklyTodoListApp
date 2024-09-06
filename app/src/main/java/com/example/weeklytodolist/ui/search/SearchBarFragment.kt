package com.example.weeklytodolist.ui.search

import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.task.TaskDetailDestination
import com.example.weeklytodolist.ui.task.TaskListFragment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFragment(
    modifier: Modifier = Modifier,
    searchResultViewModel: SearchResultViewModel = viewModel(factory = ViewModelProvider.Factory),
    onCardClicked: (Task) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchUiState by remember {
        derivedStateOf {
            searchResultViewModel.searchUiState
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
                    Log.d("SearchBar: $it","Is empty: ${it.isEmpty()}")
                    if (it.isNotEmpty()) {
                        Log.d("SearchBar:", it)
                        searchResultViewModel.onQueryChange(it)
                        searchResultViewModel.onTyping()
                    } else searchResultViewModel.onClearing()
                },
                onSearch = {
                    searchResultViewModel.onSearch()
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                leadingIcon = {
                    IconButton(onClick = {
                        if(expanded) {
                            searchResultViewModel.onClearing()
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
                placeholder = { Text(text = stringResource(id = R.string.search_task)) }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        Column{
            val type = searchUiState.type
            Text(
                text = type.name,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            when (type) {
                TypeList.Recent, TypeList.Suggestions ->
                    ListOptionsFragment(
                        listOptions = searchUiState.toShow,
                        searchValue = searchUiState.searchValue,
                        onOptionClicked = {
                            keyboardController?.hide()
                            searchResultViewModel.onQueryChange(it)
                            searchResultViewModel.onSearch()
                        }
                    )
                else -> {
                    TaskListFragment(
                        listTasks = searchUiState.toShow,
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