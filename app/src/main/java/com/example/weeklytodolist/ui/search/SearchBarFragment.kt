package com.example.weeklytodolist.ui.search

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.ViewModelProvider

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
                query = searchResultViewModel.searchValue,
                onQueryChange = {
                    Log.d("SearchBar:", it)
                    searchResultViewModel.onQueryChange(it)
                },
                onSearch = {
                    expanded = false
                    navController.navigate(SearchScreenDestination.route)
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                leadingIcon = {
                    // Expanded -> on searching -> arrow back
                    // else -> Search icon -> when click expanded = true
                    IconButton(onClick = { expanded = !expanded }
                    ) {
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
        //TODO: This is used to show the recent searches and suggestions
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSearchBar() {
    Surface {
        var expanded by rememberSaveable { mutableStateOf(false) }

        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = "",
                    onQueryChange = {
//                        searchResultViewModel.onQueryChange(it)
                    },
                    onSearch = {
                        expanded = false
//                        navController.navigate(SearchScreenDestination.route)
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    leadingIcon = {
                        // Expanded -> on searching -> arrow back
                        // else -> Search icon -> when click expanded = true
                        IconButton(onClick = { expanded = !expanded }
                        ) {
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
            onExpandedChange = { expanded = it }
        ) {
            //TODO: This is used to show the recent searches and suggestions
        }
    }
}