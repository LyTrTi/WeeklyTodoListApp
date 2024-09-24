package com.example.weeklytodolist.data.userPreferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val recentSearches: Flow<Set<String>> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[RECENT_SEARCHES] ?: setOf()
        }

    private companion object {
        val RECENT_SEARCHES = stringSetPreferencesKey("RECENT_SEARCHES")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun addRecentSearch(searchValue: String) {
        if (searchValue.isNotEmpty()) {
            dataStore.edit { preference ->
                val currentSearches = preference[RECENT_SEARCHES] ?: emptySet()
                val updatedSearches = currentSearches.plus(searchValue)
                preference[RECENT_SEARCHES] = updatedSearches
            }
        } else {
            //Do nothing
        }
    }

    suspend fun clearSearches() {
        dataStore.edit {
            it.clear()
        }
    }
}