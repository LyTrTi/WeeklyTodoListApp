package com.example.weeklytodolist.ui.fragment

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
fun TaskActionButtonFragment(
    modifier: Modifier = Modifier,
    imageVector: ImageVector
) {
    FloatingActionButton(modifier = modifier, onClick = { /*TODO*/ }) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}

@Preview
@Composable
fun PreviewTaskActionButtonFragment() {
    Surface {
         TaskActionButtonFragment(imageVector = Icons.Filled.Edit)
    }
}