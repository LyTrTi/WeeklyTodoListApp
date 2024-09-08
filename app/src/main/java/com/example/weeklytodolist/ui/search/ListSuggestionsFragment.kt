package com.example.weeklytodolist.ui.search

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.model.Task

@Composable
fun ListSuggestionsFragment(
    modifier: Modifier = Modifier,
    listOptions: List<Task>,
    searchValue: String = "",
    onOptionClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(items = listOptions, key = { task -> task.id }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(20),
                onClick = { onOptionClicked(it.name) }
            ) {
                FormatOption(
                    modifier = Modifier.padding(6.dp),
                    searchValue = searchValue,
                    option = it.name
                )
            }
        }
    }
}

@Composable
private fun FormatOption(
    modifier: Modifier = Modifier,
    searchValue: String,
    option: String
) {
    Log.d("Format Option", "search = $searchValue and option = $option")
    val regex = Regex(pattern = "$searchValue(.+)", options = setOf(RegexOption.IGNORE_CASE))
    val subOption = regex.findAll(option).map {
        it.groupValues[1]
    }.joinToString()
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier,
            text = searchValue,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            text = subOption
        )
    }
}
