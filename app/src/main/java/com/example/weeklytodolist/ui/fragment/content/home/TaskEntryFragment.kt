@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weeklytodolist.ui.fragment.content.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskEntryFragment(
    modifier: Modifier,
    headerTitle: String,
    scaffoldState: BottomSheetScaffoldState,
    onSaveClicked: (String, String) -> Unit,
    onCancelClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    //TODO: Replace by Bottom Sheet Scaffold
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column {
                TaskEntryHeader(title = headerTitle)
                TaskEntryBody(
                    onCancelClicked = onCancelClicked,
                    onSaveClicked = onSaveClicked
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
    onSaveClicked: (String, String) -> Unit,
    onCancelClicked: () -> Unit
) {
    Column(
        modifier = modifier.padding(
            vertical = 8.dp,
            horizontal = 16.dp
        )
    ) {
        InputTextRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = "Name",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputTextRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(128.dp),
            placeholder = "Name",
            onValueChange = {}
        )

        ButtonRow(
            modifier = Modifier.fillMaxWidth(),
            onSaveClicked = { name, description ->
                onSaveClicked(name, description)
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
fun InputTextRow(
    modifier: Modifier = Modifier,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = "",
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = placeholder) },
        shape = RoundedCornerShape(16.dp),
    )
}

@Composable
fun ButtonRow(
    modifier: Modifier = Modifier,
    onSaveClicked: (String, String) -> Unit,
    onCancelClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        // TODO:
        Button(onClick = { onSaveClicked("", "") }, enabled = false) {
            Text(text = "Cancel")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onCancelClicked() }) {
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
fun PreviewTaskEntryBody() {
    TaskEntryBody(
        onSaveClicked = { name, desc -> },
        onCancelClicked = {}
    )
}