package com.android.learning.todo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao



@Composable
fun TodoItem(modifier: Modifier = Modifier,task: Task,onDelete : () -> Unit) {
    var checked by remember { mutableStateOf(false) }
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        Text(task.title, textAlign = TextAlign.Start, modifier = Modifier.padding(1.dp).weight(1f))
        Icon(
            Icons.Filled.Delete,
            contentDescription = "delete",
            modifier = Modifier
                .clickable {
                    onDelete()
                }

        )

    }
}



@Composable
fun FABButton(taskDao: TaskDao) {
    LargeFloatingActionButton(
        onClick = {  },
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Localized description",
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
        )
    }

}


