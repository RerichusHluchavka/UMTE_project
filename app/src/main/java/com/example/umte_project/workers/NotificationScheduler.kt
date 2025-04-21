package com.example.umte_project.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequestBuilder
import com.example.umte_project.data.local.SettingsRepository
import kotlinx.coroutines.flow.first

class NotificationScheduler(
    private val context: Context,
    private val settingsRepository: SettingsRepository
) {

    suspend fun schedulePeriodicNotifications() {
        val workManager = WorkManager.getInstance(context)

        // Cancel any existing work
        workManager.cancelAllWorkByTag("high_priority_todos")
        workManager.cancelAllWorkByTag("medium_priority_todos")
        workManager.cancelAllWorkByTag("low_priority_todos")


        val highPriorityInterval = settingsRepository.highPriorityInterval.first()
        val mediumPriorityInterval = settingsRepository.mediumPriorityInterval.first()
        val lowPriorityInterval = settingsRepository.lowPriorityInterval.first()

        val highPriorityRequest = PeriodicWorkRequestBuilder<TodoNotificationWorker>(
            highPriorityInterval.toLong(),  // Explicit conversion
            TimeUnit.MINUTES
        ).addTag("high_priority")
            .build()

        val mediumPriorityRequest = PeriodicWorkRequestBuilder<TodoNotificationWorker>(
            mediumPriorityInterval.toLong(), TimeUnit.MINUTES
        ).addTag("medium_priority")
            .build()

        val lowPriorityRequest = PeriodicWorkRequestBuilder<TodoNotificationWorker>(
            lowPriorityInterval.toLong(), TimeUnit.MINUTES
        ).addTag("low_priority")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "high_priority_todos",
            ExistingPeriodicWorkPolicy.UPDATE,
            highPriorityRequest
        )

        workManager.enqueueUniquePeriodicWork(
            "medium_priority_todos",
            ExistingPeriodicWorkPolicy.UPDATE,
            mediumPriorityRequest
        )

        workManager.enqueueUniquePeriodicWork(
            "low_priority_todos",
            ExistingPeriodicWorkPolicy.UPDATE,
            lowPriorityRequest
        )
    }
}
