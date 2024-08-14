package com.example.weeklytodolist.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskFAB(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
    imageVector: ImageVector
) {
    FloatingActionButton(modifier = modifier, onClick = { onClicked() }) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}

@Preview
@Composable
fun PreviewTaskActionButtonFragment() {
    Surface {
         TaskFAB(imageVector = Icons.Filled.Edit, onClicked = {})
    }
}