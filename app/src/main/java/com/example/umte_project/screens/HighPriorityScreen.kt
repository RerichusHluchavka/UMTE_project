package com.example.umte_project.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.umte_project.viewmodels.TodoListViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.umte_project.components.TodoItemCard
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.umte_project.data.model.TodoListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighPriorityScreen(
    viewModel: TodoListViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val highPriorityItems = viewModel.highPriorityItems.collectAsState().value
    var selectedItem by remember { mutableStateOf<TodoListItem?>(null) }

    selectedItem?.let { item ->
        TodoDetailScreen(
            item = item,
            onBack = { selectedItem = null },
            onSave = { updatedItem ->
                viewModel.updateItem(updatedItem)
                selectedItem = null
            }
        )
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("High Priority Tasks") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (highPriorityItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No high priority tasks", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(

                modifier = Modifier
                    .padding(padding)
                    .safeContentPadding()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(highPriorityItems) { item ->
                    TodoItemCard(
                        item = item,
                        onToggleComplete = { viewModel.toggleComplete(item) },
                        onDelete = { viewModel.deleteItem(item) },
                        onClick = { selectedItem = item }
                    )
                }
            }
        }
    }
}