package com.example.weeklytodolist.ui.fragment.content.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.fragment.TaskActionButtonFragment
import com.example.weeklytodolist.ui.navigation.NavigationDestination
import com.example.weeklytodolist.viewModel.TaskViewModel

object TaskDetailDestination: NavigationDestination {
    override val route: String = "detail"
    override val titleRes: Int = R.string.task_detail
    var taskIdArg = "task_id"
    val routeWithArg = "${route}/${taskIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    currentTaskId: Int,
    taskViewModel: TaskViewModel = viewModel(factory = TaskViewModel.factory)
) {
    val currentTask = taskViewModel.currentListTask.find { it.id == currentTaskId }!!
    Scaffold(topBar = { TaskDetailTopBar(scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()) },
        floatingActionButton = {
            TaskActionButtonFragment(imageVector = Icons.Filled.Edit)
        }) { innerPadding ->
        TaskDetailBody(
            modifier = Modifier.padding(horizontal = 16.dp),
            currentTask = currentTask,
            contentPadding = innerPadding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Task's Details") },
        navigationIcon = {
            IconButton(onClick = { /*TODO: Navigate up*/ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun TaskDetailBody(
    modifier: Modifier = Modifier,
    currentTask: Task,
    contentPadding: PaddingValues
) {
    Column(
        modifier = modifier.padding(top = contentPadding.calculateTopPadding()),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = currentTask.name, style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { /*TODO: Show dialog add if user want to achieve this task*/ }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            }
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
        Text(text = currentTask.description, style = MaterialTheme.typography.bodyMedium)
    }
}