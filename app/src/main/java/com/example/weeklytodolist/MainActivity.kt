package com.example.weeklytodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.weeklytodolist.ui.theme.WeeklyTodoListTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeeklyTodoListTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WeeklyTodoApp()
                }
            }
        }
    }
}

fun main() {
    val text = "test task 1"
    val regex = Regex("test(.+)")

    val matches = regex.findAll(text)
    val names = matches.map { it.groupValues[1] }.joinToString()
    println(names) // Alice, Bob, Eve
}