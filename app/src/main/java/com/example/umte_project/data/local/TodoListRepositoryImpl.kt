package com.example.umte_project.data.local

import com.example.umte_project.data.model.TodoListItem
import kotlinx.coroutines.flow.Flow

class TodoListRepositoryImpl (private val todoListDao: TodoListDao):TodoListRepository{
    override val wholeList: Flow<List<TodoListItem>>
        get() = todoListDao.getWholeList()

    override suspend fun delete(todoListItem: TodoListItem) {
        todoListDao.delete(todoListItem)
    }

    override suspend fun insert(todoListItem: TodoListItem) {
        todoListDao.insert(todoListItem)
    }

    override suspend fun update(todoListItem: TodoListItem) {
        todoListDao.update(todoListItem)
    }


    override suspend fun getAllTodosOnce(): List<TodoListItem> {
        return todoListDao.getWholeListOnce()
    }

}