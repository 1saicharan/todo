package com.android.learning.todo.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TaskDao {

    @Upsert
    fun insertTask(task: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<TaskEntity>?

    @Query("SELECT * FROM tasks WHERE taskId = :id")
    fun getTask(id: Int): TaskEntity?

}