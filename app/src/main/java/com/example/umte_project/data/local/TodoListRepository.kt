package com.example.umte_project.data.local

import com.example.umte_project.data.model.TodoListItem
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {
    val wholeList: Flow<List<TodoListItem>>

    suspend fun insert(todoListItem: TodoListItem)
    suspend fun update(todoListItem: TodoListItem)
    suspend fun  delete(todoListItem: TodoListItem)
}