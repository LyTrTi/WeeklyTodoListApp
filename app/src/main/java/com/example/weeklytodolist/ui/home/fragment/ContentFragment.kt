package com.example.weeklytodolist.ui.home.fragment

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.ui.home.HomeScreenViewModel
import com.example.weeklytodolist.ui.task.TaskListFragment

@Composable
fun ContentFragment(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(
            top = contentPaddingValues.calculateTopPadding(),
            bottom = contentPaddingValues.calculateBottomPadding()
        )
    ) {
        content()
    }
}