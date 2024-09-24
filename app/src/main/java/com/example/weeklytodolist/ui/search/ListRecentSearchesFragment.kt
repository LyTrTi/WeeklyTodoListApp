package com.example.weeklytodolist.ui.search

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
import androidx.compose.ui.unit.dp


@Composable
fun ListRecentSearchesFragment(
    modifier: Modifier = Modifier,
    listOptions: List<String>,
    onOptionClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(items = listOptions, key = { it }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(20),
                onClick = { onOptionClicked(it) }
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = it
                )
            }
        }
    }
}