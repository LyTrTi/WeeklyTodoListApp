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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.model.utils.getDate
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.home.TaskFAB
import com.example.weeklytodolist.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

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

    TaskEntryFragment(
        modifier = Modifier,
        headerTitle = "Edit Task",
        currentTask = uiState.value,
        scaffoldState = bottomSheetScaffoldState
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TaskDetailTopBar(
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                    title = uiState.value.name,
                    onBackButton = { navController.navigateUp() }
                )
            },
            floatingActionButton = {
                TaskFAB(
                    imageVector = Icons.Filled.Edit,
                    onClicked = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        ) { innerPadding ->
            TaskDetailBody(
                modifier = Modifier.padding(16.dp),
                currentTask = uiState.value,
                contentPadding = innerPadding,
                onChecked = {
                    taskDetailsViewModel.unDone()
                }
            )
        }
    }
//
//    Scaffold(
//        topBar = {
//            TaskDetailTopBar(
//                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
//                title = uiState.value.name,
//                onBackButton = { navController.navigateUp() }
//            )
//        }
//    ) { innerPadding ->
//        TaskDetailBody(
//            modifier = Modifier.padding(horizontal = 16.dp),
//            currentTask = uiState.value,
//            contentPadding = innerPadding,
//            onChecked = {
//                taskDetailsViewModel.unDone()
//            }
//        )
//    }
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
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
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
    onChecked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = contentPadding.calculateTopPadding())
            .padding(horizontal = 16.dp),
    ) {
        Text(text = currentTask.name, style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()
        RowAttribute(
            modifier = Modifier.fillMaxWidth(),
            title = "Complete"
        ) {
            Checkbox(
                modifier = Modifier.weight(1f),
                checked = currentTask.done, onCheckedChange = {
                    onChecked()
                }
            )
        }
        RowAttribute(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            title = "Date"
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = currentTask.getDate()
            )
        }
        HorizontalDivider()
        Text(text = currentTask.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun RowAttribute(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.W500
        )
        content()
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
        onChecked = {}
    )
}