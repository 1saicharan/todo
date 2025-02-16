package com.android.learning.todo.data.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "tasks")
data class TaskEntity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val title: String,
    val description: String = "",
    val dueDate: LocalDate = LocalDate.now(),
    var isCompleted: Boolean = false,
    val userId: Int
)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val name:String,
    val username: String,
    val password: String
)