package com.example.weeklytodolist.data.firebase.firestore

data class Task(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val isArchive: Boolean,
    val dateTime: String,
)
