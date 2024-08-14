package com.example.weeklytodolist.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.home.HomeDestination
import com.example.weeklytodolist.ui.home.HomeScreen
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.task.TaskDetailDestination
import com.example.weeklytodolist.ui.task.TaskDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenNavHost(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(route = HomeDestination.route) {
            val homeScreenViewModel: HomeScreenViewModel =
                viewModel(factory = ViewModelProvider.Factory)
            val homeUiState by homeScreenViewModel.homeUiState.collectAsState()

//            LaunchedEffect(homeUiState) {
//                Log.d("DEBUG: Trigger homeUiState", "AppNavGraph")
//                homeScreenViewModel.tabChanged()
//            }

            HomeScreen(
                modifier = Modifier,
                homeUiState = homeUiState,
                navController = navController,
            )
        }
        composable(
            route = TaskDetailDestination.routeWithArg,
            arguments = listOf(navArgument(TaskDetailDestination.taskIdArg) {
                type = NavType.IntType
            })
        ) {
            TaskDetailScreen(currentTaskId = 0)
        }
    }
}