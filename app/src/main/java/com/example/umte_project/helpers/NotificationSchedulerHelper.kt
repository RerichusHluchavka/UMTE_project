package com.example.umte_project.helpers

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.umte_project.data.local.SettingsRepository
import com.example.umte_project.workers.TodoNotificationWorker
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class NotificationSchedulerHelper(
    private val context: Context,
    private val settingsRepository: SettingsRepository
) {
    /*
        suspend fun cancelExisting(){
            val workManager = WorkManager.getInstance(context)

            workManager.cancelAllWorkByTag("high_priority_todos")
            workManager.cancelAllWorkByTag("medium_priority_todos")
            workManager.cancelAllWorkByTag("low_priority_todos")
        }
    */
    suspend fun schedulePeriodicNotifications() {
        val workManager = WorkManager.getInstance(context)

        Log.wtf("TAG", "cancel culture")

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val highPriorityInterval = settingsRepository.highPriorityInterval.first()
        val mediumPriorityInterval = settingsRepository.mediumPriorityInterval.first()
        val lowPriorityInterval = settingsRepository.lowPriorityInterval.first()

        Log.wtf(
            "TAG",
            "cancel culture: $highPriorityInterval -- $mediumPriorityInterval -- $lowPriorityInterval"
        )


        val highPriorityRequest = PeriodicWorkRequestBuilder<TodoNotificationWorker>(
            highPriorityInterval.toLong(),
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .setInputData(workDataOf("priority" to "high"))
            .build()

        val mediumPriorityRequest = PeriodicWorkRequestBuilder<TodoNotificationWorker>(
            mediumPriorityInterval.toLong(), TimeUnit.MINUTES
        ).setConstraints(constraints)
            .setInputData(workDataOf("priority" to "medium"))
            .build()

        val lowPriorityRequest = PeriodicWorkRequestBuilder<TodoNotificationWorker>(
            lowPriorityInterval.toLong(), TimeUnit.MINUTES
        ).setConstraints(constraints)
            .setInputData(workDataOf("priority" to "low"))
            .build()

        workManager.enqueueUniquePeriodicWork(
            "high_priority_todos",
            ExistingPeriodicWorkPolicy.REPLACE,
            highPriorityRequest
        )

        workManager.enqueueUniquePeriodicWork(
            "medium_priority_todos",
            ExistingPeriodicWorkPolicy.REPLACE,
            mediumPriorityRequest
        )

        workManager.enqueueUniquePeriodicWork(
            "low_priority_todos",
            ExistingPeriodicWorkPolicy.REPLACE,
            lowPriorityRequest
        )

    }
}