package com.android.learning.todo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.toTask
import com.android.learning.todo.data.toTaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {

    private val _userId = MutableStateFlow<Int?>(null)  // Store the logged-in user ID
    val userId: StateFlow<Int?> = _userId.asStateFlow()

    fun setUser(userId: Int) {
        _userId.value = userId
    }


    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insertTask(task.toTaskEntity())
        }

    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(task.toTaskEntity())
        }
    }

    fun getTasks(dueDate: LocalDate): Flow<List<Task>> {
        return _userId.filterNotNull().flatMapLatest { userId ->
            taskDao.getTasks(dueDate, userId).map { it.map { it.toTask() } }
        }.flowOn(Dispatchers.IO)
    }

}