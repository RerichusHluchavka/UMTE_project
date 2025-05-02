package com.example.umte_project.viewmodels

import android.app.Application
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umte_project.data.local.SettingsRepository
import com.example.umte_project.helpers.NotificationSchedulerHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val application: Application

) : ViewModel() {

    private val _highPriorityInterval = MutableStateFlow(120) // Default value
    val highPriorityInterval: StateFlow<Int> = _highPriorityInterval.asStateFlow()

    private val _mediumPriorityInterval = MutableStateFlow(360) // Default value
    val mediumPriorityInterval: StateFlow<Int> = _mediumPriorityInterval.asStateFlow()

    private val _lowPriorityInterval = MutableStateFlow(1440) // Default value
    val lowPriorityInterval: StateFlow<Int> = _lowPriorityInterval.asStateFlow()



    init {
        viewModelScope.launch {
            settingsRepository.highPriorityInterval.collect { interval ->
                _highPriorityInterval.value = interval
            }
        }
        viewModelScope.launch {
            settingsRepository.mediumPriorityInterval.collect { interval ->
                _mediumPriorityInterval.value = interval
            }
        }
        viewModelScope.launch {
            settingsRepository.lowPriorityInterval.collect { interval ->
                _lowPriorityInterval.value = interval
            }
        }
    }

    fun setHighPriorityInterval(minutes: Int) {
        _highPriorityInterval.value = minutes.coerceIn(15, 1440)
        viewModelScope.launch {
            settingsRepository.setNotificationInterval("high_priority",minutes)
        }
    }

    fun setMediumPriorityInterval(minutes: Int) {
        _mediumPriorityInterval.value = minutes.coerceIn(15, 1440)
        viewModelScope.launch {
            settingsRepository.setNotificationInterval("medium_priority",minutes)
        }
    }

    fun setLowPriorityInterval(minutes: Int) {
        _lowPriorityInterval.value = minutes.coerceIn(15, 1440)
        viewModelScope.launch {
            settingsRepository.setNotificationInterval("low_priority",minutes)
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            try {
                // 1. Save all intervals in parallel for better performance
                val saveHigh = async {
                    settingsRepository.setNotificationInterval("high_priority", highPriorityInterval.value)
                }
                val saveMedium = async {
                    settingsRepository.setNotificationInterval("medium_priority", mediumPriorityInterval.value)
                }
                val saveLow = async {
                    settingsRepository.setNotificationInterval("low_priority", lowPriorityInterval.value)
                }

                awaitAll(saveHigh, saveMedium, saveLow)

                withContext(Dispatchers.IO) {
                    NotificationSchedulerHelper(application, settingsRepository).schedulePeriodicNotifications()
                }


            } catch (e: Exception) {
                Log.e("SettingsVM", "Save failed", e)
            }
        }
    }
}