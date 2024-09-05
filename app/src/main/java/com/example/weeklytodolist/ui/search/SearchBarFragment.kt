package com.example.weeklytodolist.ui.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.task.TaskDetailDestination
import com.example.weeklytodolist.ui.task.TaskListFragment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFragment(
    modifier: Modifier = Modifier,
    navController: NavController,
    searchResultViewModel: SearchResultViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = searchResultViewModel.searchUiState.searchValue,
                onQueryChange = {
                    Log.d("SearchBar:","Is empty: ${it.isEmpty()}")
                    if (it.isNotEmpty()) {
                        Log.d("SearchBar:", it)
                        searchResultViewModel.onQueryChange(it)
                    } else searchResultViewModel.onClearing()
                },
                onSearch = {
                    expanded = false
                    searchResultViewModel.onSearch()
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                leadingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
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
            val type = searchResultViewModel.searchUiState.type
            Text(
                text = type.name,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            when (type) {
                TypeList.Recent, TypeList.Suggestions ->
                    ListOptionsFragment(
                        listOptions = searchResultViewModel.searchUiState.toShow,
                        searchValue = searchResultViewModel.searchUiState.searchValue
                    )

                else -> {
                    TaskListFragment(
                        listTasks = searchResultViewModel.searchUiState.toShow,
                        onDoneClicked = {
                            //Do Nothing
                        }
                    ) {
                        navController.navigate("${TaskDetailDestination.route}/${it.id}")
                    }
                }
            }
        }
    }
}