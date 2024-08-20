package com.example.weeklytodolist.ui.task

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.home.TaskFAB
import com.example.weeklytodolist.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

private val TAG = "DETAIL:"

object TaskDetailDestination : NavigationDestination {
    override val route: String = "detail"
    override val titleRes: Int = R.string.task_detail
    const val TASK_ID_ARG = "task_id"
    val routeWithArg = "$route/{$TASK_ID_ARG}" // detail/task_id
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
    Log.d(TAG, "TaskDetailScreen: ${uiState.value.name}")
    //TODO: Bottom sheet scaffold
    TaskEntryFragment(
        modifier = Modifier,
        headerTitle = stringResource(id = R.string.EditTask),
        scaffoldState = bottomSheetScaffoldState,
        scope = scope,
    ) {
        Scaffold(
            topBar = {
                TaskDetailTopBar(
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                    title = uiState.value.name,
                    onBackButton = { navController.navigateUp() }
                )
            },
            floatingActionButton = {
                TaskFAB(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    onClicked = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.show()
                        }
                    }
                )
            }
        ) { innerPadding ->
            TaskDetailBody(
                modifier = Modifier.padding(horizontal = 16.dp),
                currentTask = uiState.value,
                contentPadding = innerPadding
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopBar(scrollBehavior: TopAppBarScrollBehavior, title: String, onBackButton: () -> Unit) {
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
    currentTask: TaskDetails,
    contentPadding: PaddingValues
) {
    Column(
        modifier = modifier.padding(top = contentPadding.calculateTopPadding()),
    ) {
        Text(text = currentTask.description, style = MaterialTheme.typography.bodyMedium)
    }
}