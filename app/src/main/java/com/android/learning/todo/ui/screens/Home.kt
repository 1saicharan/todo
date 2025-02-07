package com.android.learning.todo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.toTask
import com.android.learning.todo.data.toTaskEntity
import com.android.learning.todo.ui.components.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun HomeScreen(modifier: Modifier = Modifier,listOfItems: List<Task> = emptyList(),taskDao: TaskDao) {
    var description by remember { mutableStateOf("") }
    var listOfItems by remember { mutableStateOf(listOfItems) }
    Column(modifier = modifier
        .padding(5.dp)
        .fillMaxHeight()) {
        Row() {
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.padding(5.dp),
            )
            Button(onClick = { if(description.isNotBlank()){
                runBlocking {  CoroutineScope(Dispatchers.IO).launch {
                    taskDao.insertTask(Task(description = description).toTaskEntity())
                }}
                description = ""
            } }, modifier = Modifier.padding(5.dp),) {
                Text("Add")
            }

        }
        LazyColumn(modifier = Modifier.weight(1f).padding((5.dp))) {
            items(listOfItems) { item ->
              TodoItem(title = item.description)
            }
        }
    }

}
