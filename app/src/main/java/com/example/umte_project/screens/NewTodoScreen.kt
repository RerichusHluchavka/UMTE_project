package com.example.umte_project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize

@Composable
fun NewTodoScreen(
    onSave: (title: String, description: String, priority: Int) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableIntStateOf(1) }
    val isSaveEnabled = title.isNotBlank()
    var expanded by remember { mutableStateOf(false) }


    val priorities = listOf(0, 1, 2, 3, 4, 5)
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "New Todo Item",
            style = MaterialTheme.typography.headlineMedium
        )

        // Title and Priority Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Title Field (takes 70% width)
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title *") },
                modifier = Modifier.weight(0.7f),
                singleLine = true
            )

            // Priority Dropdown (takes 30% width)
            Box(
                modifier = Modifier.weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    OutlinedTextField(
                        value = priority.toString(),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        },
                        modifier = Modifier
                            .width(100.dp)
                            .onGloballyPositioned { coordinates ->
                                // This value is used to assign to
                                // the DropDown the same width
                                mTextFieldSize = coordinates.size.toSize()
                            },
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        priorities.forEach { level ->
                            DropdownMenuItem(
                                text = { Text("$level") },
                                onClick = {
                                    priority = level
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            maxLines = 3
        )

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onSave(title, description, priority) },
                enabled = isSaveEnabled
            ) {
                Text("Save")
            }
        }
    }
}
