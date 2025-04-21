package com.example.umte_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.umte_project.screens.HighPriorityScreen
import com.example.umte_project.screens.SettingsScreen
import com.example.umte_project.screens.TodoListScreen

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "todoList"
    ) {
        composable("todoList") {
            TodoListScreen(
                onNavigateToHighPriority = {
                    navController.navigate("highPriority")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )

        }
        composable("highPriority") {
            HighPriorityScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}