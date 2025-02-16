package com.android.learning.todo.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.learning.todo.data.Task
import com.android.learning.todo.ui.components.TodoItem
import com.android.learning.todo.viewmodels.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDate


@Composable
fun TodoListScreen(modifier: Modifier = Modifier,taskViewModel: TaskViewModel,navController: NavHostController,date: LocalDate) {
    var title by remember { mutableStateOf("") }
    val userId by taskViewModel.userId.collectAsState()
    val listOfItems by taskViewModel.getTasks(dueDate = date).collectAsState(initial = emptyList())

    val focusManager = LocalFocusManager.current // Manages focus

    LaunchedEffect(Unit) {
        focusManager.clearFocus() // Clears focus when screen opens
    }
    Column(modifier = modifier
        .padding(6.dp)
        .fillMaxHeight()
        .imePadding()) {
        CalenderScreen(
            navController = navController,
        )
        Row() {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.padding(5.dp),
            )
            Button(onClick = { if(title.isNotBlank()){
                runBlocking {  withContext(Dispatchers.IO) {
                    taskViewModel.insertTask(Task(title = title, dueDate = date, userId = userId!!),)
                }}
                title = ""
            } }, modifier = Modifier.padding(5.dp),) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }

        }
        Text(text = "Tasks for date - $date", modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Justify)
        LazyColumn(modifier = Modifier.weight(1f).padding((5.dp))) {
            items(listOfItems) { item ->
              TodoItem(task = item, onDelete = {taskViewModel.deleteTask(task = item)})
            }
        }
    }

}

