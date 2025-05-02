package com.example.umte_project.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.umte_project.data.local.TodoListRepository
import com.example.umte_project.data.model.TodoListItem
import com.example.umte_project.helpers.NotificationHelper
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoNotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {
    private val notificationHelper = NotificationHelper(context)
    private val repository: TodoListRepository by inject()

    override fun doWork(): Result {
        val todos = runBlocking { repository.getAllTodosOnce() }

        val notificationPriority = inputData.getString("priority") ?: "low"

        val highPriority = todos.filter { it.priority == 5 }
        val mediumPriority = todos.filter { it.priority in 3..4 }
        val lowPriority = todos.filter { it.priority in 1..2 }


        if (highPriority.isNotEmpty() && notificationPriority == "high") {
            showPriorityNotification(highPriority, "High Priority Tasks")
        }

        if (mediumPriority.isNotEmpty() && notificationPriority == "medium") {
            showPriorityNotification(mediumPriority, "Medium Priority Tasks")
        }

        if (lowPriority.isNotEmpty() && notificationPriority == "low") {
            showPriorityNotification(lowPriority, "Low Priority Tasks")
        }

        return Result.success()
    }

    private fun showPriorityNotification(todos: List<TodoListItem>, title: String) {
        val content = when {
            todos.size <= 3 -> todos.joinToString("\n") { it.title }
            else -> "You have ${todos.size} $title waiting"
        }
        notificationHelper.showNotification(title, content)
    }

}
