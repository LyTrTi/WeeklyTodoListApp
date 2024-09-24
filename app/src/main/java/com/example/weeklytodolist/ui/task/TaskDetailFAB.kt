package com.example.weeklytodolist.ui.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskDetailFAB(
    modifier: Modifier = Modifier,
    items: List<FABItem>
) {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "")

    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(tween(1500)) + fadeIn(),
                exit = shrinkVertically(tween(1200)) + fadeOut(
                    animationSpec = tween(1000)
                )
            ) {
                Column {
                    for (item in items) {
                        IconButton(
                            enabled = item.enable,
                            onClick = {
                                item.onClicked()
                                expanded = false
                            }
                        ) {
                            Icon(imageVector = item.icon, contentDescription = null)
                        }
                    }

                }
            }

            // Main FAB
            Card(
                onClick = {
                    expanded = !expanded
                }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun PreviewTaskActionButtonFragment() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TaskDetailFAB(
            items = listOf(
                FABItem(
                    icon = Icons.Default.Delete,
                    name = "Delete task",
                    onClicked = {}
                ),
                FABItem(
                    icon = Icons.Default.Edit,
                    name = "Delete task",
                    onClicked = {}
                ),
                FABItem(
                    icon = Icons.Default.ArrowDropDown,
                    name = "Delete task",
                    onClicked = {}
                )
            )
        )
    }
}