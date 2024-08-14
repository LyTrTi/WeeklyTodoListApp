package com.example.weeklytodolist.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.task.TaskListFragment

//TODO: This function should have a navController argument
//TODO: maybe also viewModel, or just a function to call when a task is set to done.
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentFragment(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = ViewModelProvider.Factory),
    contentPaddingValues: PaddingValues,
    onCardClicked: (Task) -> Unit,
) {
    Column(
        modifier = modifier.padding(
            top = contentPaddingValues.calculateTopPadding(),
            bottom = contentPaddingValues.calculateBottomPadding()
        )
    ) {
        DateListFragment(modifier = Modifier.padding(8.dp))
        HorizontalDivider()
        TaskListFragment(
            modifier = Modifier,
            listTasks = homeScreenViewModel.tabState.currentList,
            onDoneClicked = {
                Log.d("DEBUG: ContentFragment", homeScreenViewModel.tabState.tab.name)
                homeScreenViewModel.markAsDone(it)
            },
            onCardClicked = {
                onCardClicked(it)
            }
        )
    }
}