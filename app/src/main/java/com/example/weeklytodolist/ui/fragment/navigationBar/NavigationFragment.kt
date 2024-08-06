package com.example.weeklytodolist.ui.fragment.navigationBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.viewModel.TaskViewModel
import com.example.weeklytodolist.viewModel.TypeList

@Composable
fun BottomNavigation(
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationItem(title = "Task", icon = Icons.Default.Menu, onClick = {
            taskViewModel.updateCurrentListTask(TypeList.DEFAULT.name)
        })
        NavigationItem(title = "Achieve", icon = Icons.Default.Star, onClick = {
            taskViewModel.updateCurrentListTask(TypeList.ACHIEVE.name)
        })
        NavigationItem(title = "Done", icon = Icons.Default.Done, onClick = {
            taskViewModel.updateCurrentListTask(TypeList.DONE.name)
        })
    }
}

@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    // TODO: The trigger to navigate
    val defaultColor = Color.Red
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick() },
            colors = IconButtonDefaults.iconButtonColors(defaultColor)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
        Text(modifier = Modifier, text = title)
    }
}