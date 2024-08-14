package com.example.weeklytodolist.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.model.Date

@Composable
fun DateListFragment(
    modifier:Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(items = dayOfWeek, key = { day -> day.id }) { day ->
            DateItem(day = day.name)
        }
    }
}

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    day: String
) {
    Box(
        //TODO: create value in values.xml
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Min)
            .padding(4.dp)
            .border(0.3.dp, Color.Black, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
            text = day,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateItem() {
    Surface {
        DateItem(day = "Monday")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateListFragment() {
    Surface {
        DateListFragment()
    }
}

private val dayOfWeek = listOf(
    Date(
        id = 1,
        name = "Monday"
    ),
    Date(
        id = 2,
        name = "Tuesday"
    ),
    Date(
        id = 3,
        name = "Wednesday"
    ),
    Date(
        id = 4,
        name = "Thursday"
    ),
    Date(
        id = 5,
        name = "Friday"
    ),
    Date(
        id = 6,
        name = "Saturday"
    ),
    Date(
        id = 7,
        name = "Sunday"
    ),
)