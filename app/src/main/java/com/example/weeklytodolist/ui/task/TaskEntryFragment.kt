@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weeklytodolist.ui.task

import android.view.WindowInsets
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.viewModel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntryFragment(
    modifier: Modifier,
    viewModel: TaskEntryViewModel = viewModel(factory = ViewModelProvider.Factory),
    headerTitle: String,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    //TODO: Replace by Bottom Sheet Scaffold
    val configuration = LocalConfiguration.current
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column {
                TaskEntryHeader(title = headerTitle)
                TaskEntryBody(
                    onCancelClicked = {
                        scope.launch { scaffoldState.bottomSheetState.hide() }
                    },
                    onSaveClicked = {
                        viewModel.insertTask()
                    }
                )
            }
        }
    ) {
        content()
    }
}

@Composable
fun TaskEntryBody(
    modifier: Modifier = Modifier,
    taskViewModel: TaskEntryViewModel = viewModel(factory = TaskViewModel.factory),
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Column(
        modifier = modifier.padding(
            vertical = 8.dp,
            horizontal = 16.dp
        )
    ) {
        InputTextBody(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            task = taskViewModel.taskInfo,
            onValueChange = { taskViewModel.taskInfo = it }
        )

        ButtonRow(
            modifier = Modifier.fillMaxWidth(),
            onSaveClicked = {
                onSaveClicked()
            },
            onCancelClicked = { onCancelClicked() }
        )
    }
}

@Composable
fun TaskEntryHeader(
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()
    }
}

@Composable
fun InputTextBody(
    modifier: Modifier = Modifier,
    task: TaskDetails,
    onValueChange: (TaskDetails) -> Unit
) {
    Column {
        OutlinedTextField(
            modifier = modifier,
            value = task.name,
            onValueChange = { newName ->
                onValueChange(task.copy(name = newName))
            },
            placeholder = { Text(text = "Name") },
            shape = RoundedCornerShape(16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = modifier.height(124.dp),
            value = task.description,
            onValueChange = { newDesc ->
                onValueChange(task.copy(description = newDesc))
            },
            placeholder = { Text(text = "Description") },
            shape = RoundedCornerShape(16.dp),
        )
    }
}

@Composable
fun ButtonRow(
    modifier: Modifier = Modifier,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        // TODO:
        Button(onClick = { onCancelClicked() }) {
            Text(text = "Cancel")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onSaveClicked() }) {
            Text(text = "Summit")
        }
    }
}

@Preview
@Composable
fun PreviewTaskEntryHeader() {
    Surface {
        TaskEntryHeader(title = "Edit Task")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputTextBody() {
    InputTextBody(
        task = TaskDetails(),
        onValueChange = {}
    )
}