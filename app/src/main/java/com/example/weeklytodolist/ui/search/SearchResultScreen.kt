package com.example.weeklytodolist.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.navigation.NavigationDestination
import com.example.weeklytodolist.ui.task.TaskListFragment

object SearchScreenDestination : NavigationDestination {
    override val route: String = "searchresultscreen"
    override val titleRes: Int = R.string.search_result_screen
}

@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchResultViewModel: SearchResultViewModel = viewModel(factory = ViewModelProvider.Factory),
    homeScreenViewModel: HomeScreenViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
//    searchResultViewModel.search()

}