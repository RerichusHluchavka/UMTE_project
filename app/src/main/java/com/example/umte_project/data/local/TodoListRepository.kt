package com.example.umte_project.data.local

import com.example.umte_project.data.model.TodoListItem
import kotlinx.coroutines.flow.Flow

class TodoListRepository(private val todoListDao: TodoListDao) {
    val wholeList: Flow<List<TodoListItem>> = todoListDao.getWholeList()

    suspend fun insert(todoListItem: TodoListItem) = todoListDao.insert(todoListItem)

    suspend fun update(todoListItem: TodoListItem) = todoListDao.update(todoListItem)

    suspend fun  delete(todoListItem: TodoListItem) = todoListDao.delete(todoListItem)
}