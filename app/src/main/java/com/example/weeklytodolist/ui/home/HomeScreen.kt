package com.example.weeklytodolist.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.navigation.NavigationDestination
import com.example.weeklytodolist.ui.task.TaskEntryFragment
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
    onCardClicked: (Task) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val homeUiState by homeScreenViewModel.homeUiState.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    // BottomSheet
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
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            },
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth(),
                    homeUiState = homeUiState
                )
            },
            floatingActionButton = {
                TaskFAB(
                    imageVector = Icons.Filled.Add,
                    onClicked = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        ) {
            //TODO: implement loading screen using loading status in the homeUiState
            ContentFragment(
                modifier = Modifier
                    .fillMaxSize(),
                contentPaddingValues = it,
                onCardClicked = { task ->
                    onCardClicked(task)
                }
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = "Loading...")
    }
}
