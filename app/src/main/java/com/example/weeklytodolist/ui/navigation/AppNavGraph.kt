package com.example.weeklytodolist.ui.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weeklytodolist.ui.home.HomeDestination
import com.example.weeklytodolist.ui.home.HomeScreen
import com.example.weeklytodolist.ui.search.SearchResultScreen
import com.example.weeklytodolist.ui.search.SearchScreenDestination
import com.example.weeklytodolist.ui.task.TaskDetailDestination
import com.example.weeklytodolist.ui.task.TaskDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenNavHost(
    navController: NavHostController = rememberNavController()
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope,
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
                navController = navController,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope,
            )
        }
        composable(
            route = SearchScreenDestination.route
        ) {
            SearchResultScreen(navController = navController)
        }
    }
}