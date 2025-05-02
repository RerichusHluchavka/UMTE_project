package com.example.umte_project

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.umte_project.helpers.NotificationHelper
import com.example.umte_project.helpers.PermissionHelper
import com.example.umte_project.components.TodoNavigation
import com.example.umte_project.ui.theme.UMTE_projectTheme
import com.example.umte_project.helpers.NotificationSchedulerHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {

    private val notificationSchedulerHelper: NotificationSchedulerHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionHelper = PermissionHelper(this)
        permissionHelper.requestNotificationPermission()


        if (isFirstLaunch(this)) {
            lifecycleScope.launch {
                notificationSchedulerHelper.schedulePeriodicNotifications()
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
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

