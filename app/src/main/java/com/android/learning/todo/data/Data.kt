package com.android.learning.todo.data


import java.time.LocalDate

data class Task constructor(
    val taskId: Int = 0,
    val title: String,
    val description: String = "",
//    val dueDate: LocalDate ,
    val isCompleted: Boolean = false,
)

data class User(
    val userId: Int = 0,
    val name:String,
    val username: String,
    val password: String
)
