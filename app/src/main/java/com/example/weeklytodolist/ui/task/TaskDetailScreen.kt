package com.example.weeklytodolist.ui.task

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.navigation.NavigationDestination

private val TAG = "DETAIL:"

object TaskDetailDestination : NavigationDestination {
    override val route: String = "detail"
    override val titleRes: Int = R.string.task_detail
    const val TASK_ID_ARG = "task_id"
    val routeWithArg = "$route/{$TASK_ID_ARG}" // detail/{task_id}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    taskDetailsViewModel: TaskDetailViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    val uiState = taskDetailsViewModel.uiState.collectAsState()
    Log.d("DETAIL: Viewmodel", uiState.value.toString())

    Scaffold(
        topBar = {
            TaskDetailTopBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                title = uiState.value.name,
                onBackButton = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        TaskDetailBody(
            modifier = Modifier.padding(horizontal = 16.dp),
            currentTask = uiState.value,
            contentPadding = innerPadding,
            onCheckedChanged = {
                taskDetailsViewModel.unDone()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onBackButton: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackButton() }) {
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
    contentPadding: PaddingValues,
    onCheckedChanged: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().padding(top = contentPadding.calculateTopPadding()).padding(horizontal = 16.dp),
    ) {
        Text(text = currentTask.name, style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Complete")
            Checkbox(
                checked = currentTask.done, onCheckedChange = {
                    onCheckedChanged()
                }
            )
        }
        HorizontalDivider()
        Text(text = currentTask.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskDetailBody() {
    TaskDetailBody(
        currentTask = Task(
            name = "Test",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        ),
        contentPadding = PaddingValues(0.dp),
        onCheckedChanged = {}
    )
}