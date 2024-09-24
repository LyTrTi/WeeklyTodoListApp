package com.example.weeklytodolist.ui.home.fragment

import android.icu.text.DateFormatSymbols
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.model.utils.DateFormatInfo

@Composable
fun DateListFragment(
    modifier: Modifier = Modifier,
    onDateClicked: (String) -> Unit,
) {
    var selectedDay by remember {
        mutableStateOf(DateFormatInfo.currentDayOfWeek())
    }
    val weekDays = DateFormatSymbols().weekdays.filter {
        it.isNotEmpty()
    }
    val state = rememberLazyListState(initialFirstVisibleItemIndex = weekDays.indexOf(selectedDay))
    LazyRow(modifier = modifier, state = state) {
        items(items = weekDays, key = { day -> weekDays.indexOf(day) }) { day ->
            DateItem(
                selectedDay = selectedDay,
                day = day,
                onClicked = {
                    selectedDay = day
                    onDateClicked(day)
                }
            )
        }
    }
}

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    selectedDay: String,
    day: String,
    onClicked: (String) -> Unit
) {
    val containerColor = if (selectedDay.equals(day, ignoreCase = true))
        Color.White
    else
        Color.LightGray

    Card(
        modifier = modifier.padding(2.dp),
        onClick = { onClicked(day) },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(
            width = 0.5.dp,
            brush = SolidColor(Color.Gray)
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
            text = day,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateItem() {
    Surface {
        DateItem(selectedDay = "Monday", day = "Monday", onClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateListFragment() {
    Surface {
        DateListFragment(onDateClicked = {})
    }
}
