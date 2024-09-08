package com.example.weeklytodolist.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.model.utils.DateTimeConverter
import com.example.weeklytodolist.model.utils.getDate
import java.time.format.DateTimeFormatter

@Composable
fun DefaultTaskItem(
    task: Task,
    onDoneClicked: (Task) -> Unit,
    onCardClicked: (Task) -> Unit
) {
    var isChecked by remember {
        mutableStateOf(task.done)
    }

    val icon: ImageVector =
        if (isChecked) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Card(
        modifier = Modifier.padding(4.dp),
        onClick = { onCardClicked(task) },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column (
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier,
                        text = task.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .height(24.dp),
                        text = task.description,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            isChecked = !isChecked
                            onDoneClicked(task)
                        }
                    ) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                    Text(text = task.getDate())
                }
            }
        }
    }
}

@Composable
fun OptionItems(

) {

}

@Preview(showBackground = true)
@Composable
fun PreviewTaskItem() {
    var lorem = ""
    LoremIpsum().values.forEach { lorem += it }
    Surface {
        DefaultTaskItem(
            Task(
                id = 1,
                name = "Test Item",
                description = lorem,
                dateTime = "05/09/2024"
            ),
            onDoneClicked = {},
            onCardClicked = {}
        )
    }
}