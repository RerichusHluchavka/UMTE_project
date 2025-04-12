package com.example.umte_project

import androidx.room.Room
import com.example.umte_project.data.local.TodoListDatabase
import com.example.umte_project.data.local.TodoListRepository
import com.example.umte_project.data.local.TodoListRepositoryImpl
import com.example.umte_project.viewmodels.TodoListViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TodoListDatabase::class.java,
            "todo_db"
        ).build()
    }

    single { get<TodoListDatabase>().todoListDao() }

}

val viewModelModule = module {
    viewModel { TodoListViewModel(get()) }
}

val repositoryModule = module {
    single<TodoListRepository> {
        TodoListRepositoryImpl(get()) // Injects TodoListDao
    }
}