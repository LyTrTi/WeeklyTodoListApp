package com.example.weeklytodolist

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}