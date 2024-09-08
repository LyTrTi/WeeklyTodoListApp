@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weeklytodolist.ui.task

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weeklytodolist.model.Task
import com.example.weeklytodolist.model.utils.DateFormatInfo
import com.example.weeklytodolist.model.utils.getDate
import com.example.weeklytodolist.ui.ViewModelProvider
import com.example.weeklytodolist.viewModel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntryFragment(
    modifier: Modifier,
    viewModel: TaskEntryViewModel = viewModel(factory = ViewModelProvider.Factory),
    currentTask: Task? = null,
    headerTitle: String,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    currentTask?.let {
        viewModel.newTask = currentTask
    }

    //TODO: Replace by Bottom Sheet Scaffold
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
                        scope.launch { scaffoldState.bottomSheetState.hide() }
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
        InputBody(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            task = taskViewModel.newTask,
            onValueChange = { taskViewModel.newTask = it }
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
fun InputBody(
    modifier: Modifier = Modifier,
    task: Task,
    onValueChange: (Task) -> Unit
) {
    Column {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = task.name,
            onValueChange = { newName ->
                onValueChange(task.copy(name = newName))
            },
            placeholder = { Text(text = "Name") },
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )
        TaskAttribute(
            task = task,
            onChecked = { newValue ->
                onValueChange(task.copy(done = newValue))
            },
            onDateSet = { newValue ->
                onValueChange(task.copy(dateTime = newValue))
            },
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = 16.dp))
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(124.dp),
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
fun TaskAttribute(
    task: Task,
    onChecked: (Boolean) -> Unit,
    onDateSet: (String) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    Column {
        // Check box complete
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(modifier = Modifier.weight(1f), text = "Complete")
            Checkbox(
                modifier = Modifier.weight(1f),
                checked = task.done, onCheckedChange = {
                    onChecked(!task.done)
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), text = "Date"
            )
//            Button(
//                modifier = Modifier.weight(1f),
//                colors = ButtonDefaults.buttonColors(
//                    contentColor = Color.DarkGray,
//                    containerColor = Color.Transparent
//                ),
//                onClick = { }
//            ) {
//                task.date?.let { Text(text = it) }
//            }
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { openDialog = !openDialog }
            ) {
                Text(text = task.getDate())
            }
        }
    }

    if (openDialog) {
        DatePickerScreen(
            onDismissRequest = { value -> openDialog = value }) { newValue ->
            openDialog = !openDialog
            onDateSet(newValue)
        }
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

@Composable
fun DatePickerScreen(
    onDismissRequest: (Boolean) -> Unit,
    confirmButton: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnable by derivedStateOf {
        datePickerState.selectedDateMillis != null
    }

    DatePickerDialog(
        onDismissRequest = {
            onDismissRequest(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val date = datePickerState.selectedDateMillis?.let { milliSecond ->
                        DateFormatInfo.milliSecondToLocalDateTime(milliSecond)
                    }?.format(DateFormatInfo.DateTime.DATETIME_FORMAT_PATTERN)

                    confirmButton(date!!)
                },
                enabled = confirmEnable
            ) {
                Text(text = "Submit")
            }
        },
    ) {
        DatePicker(state = datePickerState)
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
fun PreviewInputBody() {
    InputBody(
        task = Task(name = "Test", description = "No more taskDetails"),
        onValueChange = {}
    )
}

@Preview
@Composable
fun PreviewDatePicker() {
    Surface {
        DatePickerScreen(
            onDismissRequest = { value -> },
            confirmButton = {}
        )
    }
}