package com.example.weeklytodolist.ui.fragment.content.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.model.Task

@Composable
fun TaskListFragment(
    modifier: Modifier = Modifier,
    listTask: List<Task>,
    onDoneClicked: (Task) -> Unit,
    onCardClicked: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        items(items = listTask, key = { task -> task.id }) {
            TaskItem(it, onDoneClicked, onCardClicked)
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDoneClicked: (Task) -> Unit,
    onCardClicked: (Task) -> Unit
) {
    //TODO: Replace by Checkboxes icon, use painterResources
    var isChecked by remember {
        mutableStateOf(false)
    }

    var iconForNow: ImageVector =
        if (isChecked) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Card(
        modifier = Modifier.padding(4.dp),
        onClick = { onCardClicked(task) },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = task.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        //TODO: Call viewmodel to change task's state
                        isChecked = !isChecked
                        onDoneClicked(task)
                    }
                ) {
                    Icon(imageVector = iconForNow, contentDescription = null)
                }
            }
            Text(
                modifier =  Modifier
                    .height(24.dp),
                text = task.description,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskItem() {
    var lorem = ""
    LoremIpsum().values.forEach { lorem += it }
    Surface {
        TaskItem(
            Task(
                id = 1,
                name = "Test Item",
                description = lorem,
            ),
            onDoneClicked = {},
            onCardClicked = {}
        )
    }
}