package com.example.umte_project.data.local

import androidx.room.*
import com.example.umte_project.data.model.TodoListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Insert
    suspend fun insert(todo: TodoListItem)

    @Update
    suspend fun update(todo: TodoListItem)

    @Delete
    suspend fun delete(todo: TodoListItem)

    @Query("SELECT * FROM todo_list ORDER BY createdAt DESC")
    fun getWholeList(): Flow<List<TodoListItem>>
}