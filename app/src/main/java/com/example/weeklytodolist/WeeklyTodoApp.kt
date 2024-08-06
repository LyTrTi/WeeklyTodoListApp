package com.example.weeklytodolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.weeklytodolist.ui.navigation.ScreenNavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyTodoApp() {
    ScreenNavHost()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewWeeklyTodoApp() {
    WeeklyTodoApp()
}