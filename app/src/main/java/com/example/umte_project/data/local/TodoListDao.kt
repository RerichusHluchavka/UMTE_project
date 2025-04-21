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

    @Query(
        """
    SELECT * FROM todo_list 
    ORDER BY 
        CASE WHEN isCompleted = 0 THEN 0 ELSE 1 END,  
        createdAt DESC                               
"""
    )
    fun getWholeList(): Flow<List<TodoListItem>>

    @Query("SELECT * FROM todo_list WHERE isCompleted = 0 ORDER BY createdAt DESC")
    fun getWholeListOnce(): List<TodoListItem>
}