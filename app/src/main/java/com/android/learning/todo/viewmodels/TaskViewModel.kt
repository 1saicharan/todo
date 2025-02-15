package com.android.learning.todo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.toTask
import com.android.learning.todo.data.toTaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val taskDao: TaskDao):ViewModel() {



    fun insertTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insertTask(task.toTaskEntity())
        }

    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(task.toTaskEntity())
        }
    }

    fun getTasks(dueDate: LocalDate):Flow<List<Task>>{
        return taskDao.getTasks(dueDate).map { it.map { it.toTask() } }.flowOn(Dispatchers.IO)
    }

}