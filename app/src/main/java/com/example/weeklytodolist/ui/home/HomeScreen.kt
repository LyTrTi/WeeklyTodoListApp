package com.example.weeklytodolist.ui.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.home.fragment.BottomNavigation
import com.example.weeklytodolist.ui.home.fragment.ContentFragment
import com.example.weeklytodolist.ui.home.fragment.DateListFragment
import com.example.weeklytodolist.ui.home.fragment.HomeScreenFAB
import com.example.weeklytodolist.ui.home.fragment.TaskListFragment
import com.example.weeklytodolist.navigation.NavigationDestination
import com.example.weeklytodolist.ui.search.SearchBarFragment
import com.example.weeklytodolist.ui.task.TaskDetailDestination
import com.example.weeklytodolist.ui.task.TaskEntryFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
//    const val USER_ID_ARG = "user_id"
//    val routeWithArg = "${TaskDetailDestination.route}/{$USER_ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    onCardClicked: (Task) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    TaskEntryFragment(
        modifier = modifier,
        headerTitle = "Add Task",
        scaffoldState = bottomSheetScaffoldState,
    ) {
        Scaffold(
            topBar = {
                SearchBarFragment(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    onCardClicked = onCardClicked
                )
            },
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth()
                )
            },
            floatingActionButton = {
                HomeScreenFAB(
                    imageVector = Icons.Filled.Add,
                    onClicked = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        ) { paddingValues ->
            //TODO: implement loading screen using loading status in the homeUiState
            ContentFragment(
                modifier = Modifier
                    .fillMaxSize(),
                contentPaddingValues = paddingValues
            ) {
                MainScreen(
                    homeScreenViewModel = homeScreenViewModel,
                    onCardClicked = onCardClicked
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    homeScreenViewModel: HomeScreenViewModel,
    onCardClicked: (Task) -> Unit
) {
    val listState by remember {
        derivedStateOf {
            homeScreenViewModel.taskListState
        }
    }

    DateListFragment(
        modifier = Modifier.padding(8.dp),
        onDateClicked = { dayOfWeek ->
            homeScreenViewModel.dayChanged(dayOfWeek)
        }
    )
    HorizontalDivider()
    TaskListFragment(
        modifier = Modifier,
        listTasks = listState.currentList,
        onDoneClicked = { task ->
            Log.d(
                "DEBUG: ContentFragment",
                homeScreenViewModel.taskListState.tab.name
            )
            homeScreenViewModel.markAsDone(task)
        },
        onCardClicked = {
            onCardClicked(it)
        }
    )
}
