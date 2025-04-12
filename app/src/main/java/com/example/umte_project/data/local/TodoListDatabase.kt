package com.example.umte_project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.umte_project.data.model.TodoListItem

@Database(entities = [TodoListItem::class], version = 1)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoListDao
}