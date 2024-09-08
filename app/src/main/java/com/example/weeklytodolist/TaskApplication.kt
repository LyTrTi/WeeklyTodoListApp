package com.example.weeklytodolist

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.weeklytodolist.data.AppContainer
import com.example.weeklytodolist.data.DefaultAppContainer
import com.example.weeklytodolist.data.UserPreferencesRepository

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    LAYOUT_PREFERENCES_NAME
)

class TaskApplication: Application() {
    lateinit var appContainer: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer(context = this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}