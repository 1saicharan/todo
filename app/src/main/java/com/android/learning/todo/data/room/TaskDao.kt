package com.android.learning.todo.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {

    @Upsert
    fun insertTask(task: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueDate = :dueDate AND userId = :userId")
    fun getTasks(dueDate: LocalDate,userId:Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE taskId = :id")
    fun getTask(id: Int): TaskEntity?



}