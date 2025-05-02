package com.example.umte_project

import android.app.Application
import com.example.umte_project.helpers.NotificationHelper
import com.example.umte_project.helpers.NotificationSchedulerHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.getValue

class MyApp : Application() {
    private val notificationSchedulerHelper: NotificationSchedulerHelper by inject()
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        NotificationHelper(this).createNotificationChannel()

        // Initialize Koin (moved from MainActivity)
        startKoin {
            androidContext(this@MyApp)
            modules(
                viewModelModule,
                repositoryModule,
                databaseModule,
                workerModule,
                notificationModule,
                dataStoreModule
            )
        }

        appScope.launch {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            if (prefs.getBoolean("first_launch", true)) {
                notificationSchedulerHelper.schedulePeriodicNotifications()
                prefs.edit().putBoolean("first_launch", false).apply()
            }
        }
    }
}