package com.example.umte_project.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    companion object {
        val HIGH_PRIORITY_INTERVAL = intPreferencesKey("high_priority_interval")
        val MEDIUM_PRIORITY_INTERVAL = intPreferencesKey("medium_priority_interval")
        val LOW_PRIORITY_INTERVAL = intPreferencesKey("low_priority_interval")
    }

    suspend fun setNotificationInterval(priority: String, minutes: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey("${priority}_interval")] = minutes
        }
    }

    val highPriorityInterval: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[HIGH_PRIORITY_INTERVAL] ?: 120
        }

    val mediumPriorityInterval: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[MEDIUM_PRIORITY_INTERVAL] ?: 360
        }

    val lowPriorityInterval: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[LOW_PRIORITY_INTERVAL] ?: 1440
        }

}