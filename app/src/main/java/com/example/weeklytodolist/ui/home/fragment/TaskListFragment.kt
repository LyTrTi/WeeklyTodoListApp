package com.example.weeklytodolist.ui.home.fragment

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.ui.task.DefaultTaskItem

@Composable
fun TaskListFragment(
    modifier: Modifier = Modifier,
    listTasks: List<Task>,
    onDoneClicked: (Task) -> Unit,
    onCardClicked: (Task) -> Unit
) {
    Log.d("DEBUG: TaskListFragment", listTasks.toString())
    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        items(items = listTasks, key = { task -> task.id }) {
            DefaultTaskItem(it, onDoneClicked, onCardClicked)
        }
    }
}