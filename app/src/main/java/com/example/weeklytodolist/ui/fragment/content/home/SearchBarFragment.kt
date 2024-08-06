package com.example.weeklytodolist.ui.fragment.content.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarFragment(
    modifier: Modifier = Modifier
) {
    //TODO: in ViewModel, use DataStore to store the user's preference
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(50.dp),
        value = "", // TODO: <- Right here
        onValueChange = {
            //TODO: Room, data flow
        },
        leadingIcon = {
            IconButton(onClick = { /*TODO: Navigate to TaskEntry*/ }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        placeholder = { Text(text = "Search for task") },
        maxLines = 1,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    Surface {
        SearchBarFragment()
    }
}