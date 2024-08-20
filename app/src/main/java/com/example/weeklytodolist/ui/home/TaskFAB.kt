package com.example.weeklytodolist.ui.home

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskFAB(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
    imageVector: ImageVector
) {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "")
    val rotateValue by transition.animateFloat(label = "rotation") {
        if (it) 540f else 0f
    }

    FloatingActionButton(
        modifier = modifier.rotate(rotateValue),
        onClick = {
            expanded = !expanded
            onClicked()
        }
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}

@Preview
@Composable
fun PreviewTaskActionButtonFragment() {
    Surface {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            TaskFAB(imageVector = Icons.Default.KeyboardArrowUp, onClicked = {})
        }
    }
}