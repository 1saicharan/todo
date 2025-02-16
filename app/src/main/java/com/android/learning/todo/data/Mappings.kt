package com.android.learning.todo.data

import com.android.learning.todo.data.room.TaskEntity

fun TaskEntity.toTask() = Task(taskId, title, description,dueDate, isCompleted,userId )

fun Task.toTaskEntity() = TaskEntity(taskId, title, description,dueDate, isCompleted,userId )
