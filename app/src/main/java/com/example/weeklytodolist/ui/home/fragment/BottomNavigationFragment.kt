package com.example.weeklytodolist.ui.home.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weeklytodolist.R
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.home.TypeList

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    var currentTab by remember {
        mutableStateOf(homeScreenViewModel.taskListState.tab.name)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationTab(
            title = stringResource(id = R.string.default_task),
            icon = Icons.Default.Menu,
            currentTab = currentTab,
            onClick = {
                homeScreenViewModel.tabChanged(TypeList.DEFAULT.name)
                currentTab = TypeList.DEFAULT.name
            }
        )
        NavigationTab(
            title = stringResource(id = R.string.archive_task),
            icon = Icons.Default.Star,
            currentTab = currentTab,
            onClick = {
                homeScreenViewModel.tabChanged(TypeList.ARCHIVE.name)
                currentTab = TypeList.ARCHIVE.name
            }
        )
        NavigationTab(
            title = stringResource(id = R.string.done_task),
            icon = Icons.Default.Done,
            currentTab = currentTab,
            onClick = {
                homeScreenViewModel.tabChanged(TypeList.DONE.name)
                currentTab = TypeList.DONE.name
            }
        )
    }
}

@Composable
private fun NavigationTab(
    modifier: Modifier = Modifier,
    title: String,
    currentTab: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.background(
                color = if (title.equals(
                        currentTab,
                        ignoreCase = true
                    )
                ) Color.LightGray else Color.Unspecified,
                shape = CircleShape
            ),
            onClick = {
                onClick()
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
        Text(modifier = Modifier, text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationTabPreview() {
    Surface(onClick = { /*TODO*/ }) {
        NavigationTab(
            title = stringResource(id = R.string.default_task),
            icon = Icons.Default.Menu,
            currentTab = TypeList.DONE.name,
            onClick = {
            }
        )
    }
}