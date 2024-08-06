package com.example.weeklytodolist.ui.fragment.content.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.fragment.TaskActionButtonFragment
import com.example.weeklytodolist.ui.fragment.content.detail.TaskDetailDestination
import com.example.weeklytodolist.ui.fragment.navigationBar.BottomNavigation
import com.example.weeklytodolist.ui.navigation.NavigationDestination
import com.example.weeklytodolist.viewModel.TaskViewModel
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModel.factory)

    TaskEntryFragment(
        modifier = modifier,
        headerTitle = "Add Task",
        scaffoldState = bottomSheetScaffoldState,
        onSaveClicked = {name, desc ->
            taskViewModel.insertTask(name, desc)
        },
        onCancelClicked = {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        }
    ) {
        Scaffold(
            topBar = {
                SearchBarFragment(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            },
            bottomBar = { BottomNavigation(taskViewModel, modifier = Modifier.fillMaxWidth()) },
            floatingActionButton = {
                TaskActionButtonFragment(
                    imageVector = Icons.Filled.Add
                )
            }
        ) {
            ContentFragment(
                modifier = Modifier
                    .fillMaxSize(),
                taskViewModel = taskViewModel,
                contentPaddingValues = it,
                onCardClicked = { task ->
                    navController.navigate("${TaskDetailDestination.route}/${task.id}")
                }
            )
        }
    }
}