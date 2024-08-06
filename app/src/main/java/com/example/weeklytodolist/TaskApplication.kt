package com.example.weeklytodolist

import android.app.Application
import com.example.weeklytodolist.data.AppContainer
import com.example.weeklytodolist.data.DefaultAppContainer
import com.example.weeklytodolist.data.TaskDatabase

class TaskApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer(context = this)
    }
}