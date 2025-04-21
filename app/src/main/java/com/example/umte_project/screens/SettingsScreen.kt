package com.example.umte_project.screens

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.umte_project.viewmodels.SettingsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.umte_project.data.local.SettingsRepository
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.umte_project.Module.dataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,

    ) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val dataStore = context.dataStore

    val settingsRepository = remember { SettingsRepository(dataStore) }

    val viewModel: SettingsViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SettingsViewModel(settingsRepository, application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )


    val highPriorityInterval by viewModel.highPriorityInterval.collectAsState()
    val mediumPriorityInterval by viewModel.mediumPriorityInterval.collectAsState()
    val lowPriorityInterval by viewModel.lowPriorityInterval.collectAsState()

    var highInput by remember { mutableStateOf(highPriorityInterval.toString()) }
    var mediumInput by remember { mutableStateOf(mediumPriorityInterval.toString()) }
    var lowInput by remember { mutableStateOf(lowPriorityInterval.toString()) }

    highInput = highPriorityInterval.toString()
    mediumInput = mediumPriorityInterval.toString()
    lowInput = lowPriorityInterval.toString()
    ;
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(padding)
        ) {
            // High Priority
            Text("High Priority Interval (minutes)", style = MaterialTheme.typography.titleMedium)
            TextField(
                value = highInput,
                onValueChange = {
                    highInput = it
                    it.toIntOrNull()?.let { min -> viewModel.setHighPriorityInterval(min) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Current: $highPriorityInterval minutes")

            Spacer(modifier = Modifier.height(24.dp))

            // Medium Priority
            Text("Medium Priority Interval (minutes)", style = MaterialTheme.typography.titleMedium)
            TextField(
                value = mediumInput,
                onValueChange = {
                    mediumInput = it
                    it.toIntOrNull()?.let { min -> viewModel.setMediumPriorityInterval(min) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Current: $mediumPriorityInterval minutes")

            Spacer(modifier = Modifier.height(24.dp))

            // Low Priority
            Text("Low Priority Interval (minutes)", style = MaterialTheme.typography.titleMedium)
            TextField(
                value = lowInput,
                onValueChange = {
                    lowInput = it
                    it.toIntOrNull()?.let { min -> viewModel.setLowPriorityInterval(min) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Current: $lowPriorityInterval minutes")

            Spacer(modifier = Modifier.height(24.dp))

            // Save button
            Button(
                onClick = {
                    viewModel.saveSettings(); onBack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Settings")
            }
        }
    }
}