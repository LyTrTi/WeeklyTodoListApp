package com.example.weeklytodolist.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weeklytodolist.ui.authenticationScreen.AuthViewModel
import com.example.weeklytodolist.ui.authenticationScreen.LoginScreen
import com.example.weeklytodolist.ui.authenticationScreen.LoginScreenDestination
import com.example.weeklytodolist.ui.authenticationScreen.SignupScreen
import com.example.weeklytodolist.ui.authenticationScreen.SignupScreenDestination
import com.example.weeklytodolist.ui.home.HomeDestination
import com.example.weeklytodolist.ui.home.HomeScreen
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

    val authViewModel: AuthViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = LoginScreenDestination.route) {
        composable(route = LoginScreenDestination.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignup = {
                    navController.navigate(SignupScreenDestination.route)
                },
                onLoginAccept = {
                    navController.navigate(HomeDestination.route)
                }
            )
        }
        composable(route = SignupScreenDestination.route) {
            SignupScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope,
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
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                scope = scope,
                navigateBack = {
                    navController.navigateUp()
                }
            )
         }
    }
}