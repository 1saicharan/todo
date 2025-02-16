package com.android.learning.todo.data


import java.time.LocalDate

data class Task constructor(
    val taskId: Int = 0,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate = LocalDate.now(),
    val isCompleted: Boolean = false,
    val userId: Int
)

data class User(
    val userId: Int = 0,
    val name:String,
    val username: String,
    val password: String
)
