package com.example.umte_project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.umte_project.data.model.TodoListItem
import com.example.umte_project.viewmodels.TodoListViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.umte_project.components.TodoItemCard

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = koinViewModel()
) {
    val todoItems by viewModel.wholeTodoList.collectAsState()
    var showNewTodoScreen by remember { mutableStateOf(false) }


    if (showNewTodoScreen) {
        NewTodoScreen(
            onSave = { title, description, priority ->
                viewModel.addItem(TodoListItem(title = title, description = description, priority = priority))
                showNewTodoScreen = false
            },
            onCancel = { showNewTodoScreen = false }
        )
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showNewTodoScreen = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { padding ->
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(todoItems) { item ->
                    TodoItemCard(
                        item = item,
                        onToggleComplete = { viewModel.toggleComplete(item) },
                        onDelete = { viewModel.deleteItem(item) }
                    )
                }
            }
        }
    }
}