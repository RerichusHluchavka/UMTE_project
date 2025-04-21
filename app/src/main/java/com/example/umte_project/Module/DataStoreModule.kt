package com.example.umte_project.Module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore(name = "settings")

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        get<Context>().dataStore
    }
}

