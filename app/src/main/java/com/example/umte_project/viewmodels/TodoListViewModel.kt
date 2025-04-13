package com.example.umte_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.umte_project.data.local.TodoListRepository
import com.example.umte_project.data.model.TodoListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoListViewModel (private val repository: TodoListRepository) : ViewModel(){
    private val _wholeTodoList = MutableStateFlow<List<TodoListItem>>(emptyList())

    val wholeTodoList: StateFlow<List<TodoListItem>> = _wholeTodoList.asStateFlow()

    init {
        viewModelScope.launch {
            // Load initial data from repository
            repository.wholeList.collect { items ->
                _wholeTodoList.value = items
            }
        }
    }

    // Add new item
    fun addItem(item: TodoListItem) {
        viewModelScope.launch {
            repository.insert(item)  // Pass to repository
        }
    }

    fun toggleComplete(item: TodoListItem) = viewModelScope.launch {
        repository.update(item.copy(isCompleted = !item.isCompleted))
    }

    // Update item
    fun updateItem(item: TodoListItem) = viewModelScope.launch {
        repository.update(item)
    }

    // Delete item
    fun deleteItem(item: TodoListItem) = viewModelScope.launch {
        repository.delete(item)
    }
}