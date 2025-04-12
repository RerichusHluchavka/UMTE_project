package com.example.umte_project.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.umte_project.data.local.TodoListRepository

class TodoListViewModelFactory(private val repository: TodoListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            return TodoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}