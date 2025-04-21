package com.example.umte_project

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.umte_project.helpers.NotificationHelper
import com.example.umte_project.helpers.PermissionHelper
import com.example.umte_project.navigation.TodoNavigation
import com.example.umte_project.ui.theme.UMTE_projectTheme
import com.example.umte_project.workers.NotificationScheduler
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    private val notificationScheduler: NotificationScheduler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(
                viewModelModule,
                repositoryModule,
                databaseModule,
                workerModule,
                notificationModule,
                dataStoreModule

            )
        }
        val permissionHelper = PermissionHelper(this)
        permissionHelper.requestNotificationPermission()

        NotificationHelper(this).createNotificationChannel()


        if (isFirstLaunch(this)) {
            lifecycleScope.launch {
                notificationScheduler.schedulePeriodicNotifications()
            }
        }

        enableEdgeToEdge()
        setContent {
            UMTE_projectTheme {
                TodoApp()
            }
        }
    }

    fun isFirstLaunch(context: Context): Boolean {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return if (prefs.getBoolean("first_launch", true)) {
            prefs.edit().putBoolean("first_launch", false).apply()
            true
        } else {
            false
        }
    }
}

@Composable
fun TodoApp() {
    MaterialTheme {
        TodoNavigation()
    }
}

@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    UMTE_projectTheme {
        TodoApp()
    }
}

