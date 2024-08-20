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
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.home.HomeDestination
import com.example.weeklytodolist.ui.home.HomeScreen
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.task.TaskDetailDestination
import com.example.weeklytodolist.ui.task.TaskDetailScreen
import com.example.weeklytodolist.ui.task.TaskDetailViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                navController = navController,
                onCardClicked = { task ->
                    Log.d("DETAIL:", "${TaskDetailDestination.route}/${task.id}")
                    navController.navigate("${TaskDetailDestination.route}/${task.id}")
                }
            )
        }
        composable(
            route = TaskDetailDestination.routeWithArg,
            arguments = listOf(navArgument(TaskDetailDestination.TASK_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            TaskDetailScreen(
                navController = navController
            )
        }
    }
}