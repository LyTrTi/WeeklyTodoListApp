package com.example.weeklytodolist.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weeklytodolist.ui.fragment.content.detail.TaskDetailDestination
import com.example.weeklytodolist.ui.fragment.content.detail.TaskDetailScreen
import com.example.weeklytodolist.ui.fragment.content.home.HomeDestination
import com.example.weeklytodolist.ui.fragment.content.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                navController = navController
            )
        }
        composable(
            route = TaskDetailDestination.routeWithArg,
            arguments = listOf(navArgument(TaskDetailDestination.taskIdArg) {
                type = NavType.IntType })
        ) {
            TaskDetailScreen(currentTaskId = 0)
        }
    }
}