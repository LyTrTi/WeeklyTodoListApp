package com.example.weeklytodolist.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.ViewModelProvider

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationTab(
            title = stringResource(id = R.string.DefaultTask),
            icon = Icons.Default.Menu,
            taskListState = homeScreenViewModel.taskListState,
            onClick = {
                homeScreenViewModel.tabChanged(TypeList.DEFAULT.name)
            }
        )
        NavigationTab(
            title = stringResource(id = R.string.ArchiveTask),
            icon = Icons.Default.Star,
            taskListState = homeScreenViewModel.taskListState,
            onClick = {
                homeScreenViewModel.tabChanged(TypeList.ARCHIVE.name)
            }
        )
        NavigationTab(
            title = stringResource(id = R.string.DoneTask),
            icon = Icons.Default.Done,
            taskListState = homeScreenViewModel.taskListState,
            onClick = {
                homeScreenViewModel.tabChanged(TypeList.DONE.name)
            }
        )
    }
}

@Composable
private fun NavigationTab(
    modifier: Modifier = Modifier,
    taskListState: TaskListState,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                onClick()
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
        Text(modifier = Modifier, text = title)
    }
}