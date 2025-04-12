package com.example.umte_project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.umte_project.data.model.TodoListItem

// In your Screen Composable:
@Composable
fun TodoListScreen(items: List<TodoListItem>, onItemClick: (TodoListItem) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            TodoListItemComposable(item = item, onItemClick = onItemClick)
        }
    }
}

@Composable
fun TodoListItemComposable(item: TodoListItem, onItemClick: (TodoListItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.titleMedium)
            item.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { isChecked ->
                    onItemClick(item.copy(isCompleted = isChecked))
                }
            )
        }
    }
}