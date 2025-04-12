package com.example.umte_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.umte_project.data.local.TodoListRepository
import com.example.umte_project.data.model.TodoListItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoListViewModel (private val repository: TodoListRepository) : ViewModel(){

    val wholeTodoList: StateFlow<List<TodoListItem>> =
        repository.wholeList.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(item: TodoListItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: TodoListItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: TodoListItem) = viewModelScope.launch {
        repository.delete(item)
    }
}