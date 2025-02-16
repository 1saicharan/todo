package com.android.learning.todo.data

import com.android.learning.todo.data.room.TaskEntity
import com.android.learning.todo.data.room.UserEntity

fun TaskEntity.toTask() = Task(taskId, title, description,dueDate, isCompleted,userId )

fun Task.toTaskEntity() = TaskEntity(taskId, title, description,dueDate, isCompleted,userId )

fun UserEntity.toUser() = User(userId,name,username,password)

fun User.toUserEntity() = UserEntity(userId,name,username,password)