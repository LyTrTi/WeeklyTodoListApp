package com.example.weeklytodolist.ui.home

import android.icu.text.DateFormatSymbols
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DateListFragment(
    modifier: Modifier = Modifier,
    dateListViewModel: DateListViewModel = viewModel(),
    onDateClicked: (String) -> Unit,
) {
    val uiState = dateListViewModel.uiState
    val weekDays = DateFormatSymbols().weekdays.filter {
        it.isNotEmpty()
    }

    LazyRow(modifier = modifier) {
        items(items = weekDays, key = { day -> weekDays.indexOf(day) }) { day ->
            DateItem(
                uiState = uiState,
                day = day,
                onClicked = {
                    dateListViewModel.onChanged(day)
                    onDateClicked(day)
                }
            )
        }
    }
}

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    uiState: String,
    day: String,
    onClicked: (String) -> Unit
) {
    val containerColor = if (uiState.equals(day, ignoreCase = true))
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
            width = 1.dp,
            brush = SolidColor(Color.DarkGray)
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
        DateItem(uiState = "Monday", day = "Monday", onClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateListFragment() {
    Surface {
        DateListFragment(onDateClicked = {day -> })
    }
}
