package com.example.umte_project

import androidx.room.Room
import androidx.work.WorkerParameters
import com.example.umte_project.data.local.SettingsRepository
import com.example.umte_project.data.local.TodoListDatabase
import com.example.umte_project.data.local.TodoListRepository
import com.example.umte_project.data.local.TodoListRepositoryImpl
import com.example.umte_project.viewmodels.SettingsViewModel
import com.example.umte_project.viewmodels.TodoListViewModel
import com.example.umte_project.helpers.NotificationSchedulerHelper
import com.example.umte_project.workers.TodoNotificationWorker
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import androidx.datastore.core.DataStore
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.example.umte_project.Module.dataStore

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
    viewModel {
        SettingsViewModel(
            settingsRepository = get(),
            application = get()
        )
    }
}


val repositoryModule = module {
    single<TodoListRepository> {
        TodoListRepositoryImpl(get()) // Injects TodoListDao
    }
    single<SettingsRepository> {
        SettingsRepository(get()) // Requires DataStore<Preferences>
    }
}

val workerModule = module {
    factory { (params: WorkerParameters) ->
        TodoNotificationWorker(get(), params)
    }
}

val notificationModule = module {
    single {
        NotificationSchedulerHelper(get(), get())
    }
}

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        get<Context>().dataStore
    }
}