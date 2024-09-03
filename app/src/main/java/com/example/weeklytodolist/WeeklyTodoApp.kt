package com.example.weeklytodolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.weeklytodolist.ui.navigation.ScreenNavHost

@Composable
fun WeeklyTodoApp() {
    ScreenNavHost()
}

@Preview
@Composable
fun PreviewWeeklyTodoApp() {
    WeeklyTodoApp()
}