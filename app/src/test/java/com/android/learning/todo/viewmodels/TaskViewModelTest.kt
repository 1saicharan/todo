package com.android.learning.todo.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.android.learning.todo.data.Task
import com.android.learning.todo.data.room.TaskDao
import io.mockk.InternalPlatformDsl.toArray
import kotlinx.coroutines.flow.collect
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

class TaskViewModelTest {

    lateinit var taskDao: TaskDao
    lateinit var taskViewModel: TaskViewModel
    lateinit var task: Task
    @Before
    fun setUp() {
        // Setup
        taskDao = mock(TaskDao::class.java).apply {

        }
        taskViewModel = TaskViewModel(taskDao)
        task = Task(1, "title", "description", LocalDate.parse("2021-09-01"), false, 1)

    }


    @Test
    fun setUser() {
        taskViewModel.setUser(1)
        assert(taskViewModel.userId.value == 1)
        taskViewModel.setUser(2)
        assert(taskViewModel.userId.value == 2)
    }

    @Test
    fun insertTask() {
        // Insert Task
        taskViewModel.insertTask(task)
        assert(taskViewModel.getTasks(LocalDate.parse("2021-09-01")))

    }
}