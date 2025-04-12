package com.example.umte_project.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class TodoListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Int,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)