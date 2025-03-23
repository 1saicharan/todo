package com.android.learning.todo.data

import com.android.learning.todo.data.room.TaskEntity
import com.android.learning.todo.data.room.UserEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate


class MappingsTest {

    @Test
    fun taskEntityToTask() {
        val taskEntity = TaskEntity(1, "title", "description", LocalDate.now(), false, 1)
        val task = taskEntity.toTask()
        assertEquals(taskEntity.taskId, task.taskId)
        assertEquals(taskEntity.title, task.title)
        assertEquals(taskEntity.description, task.description)
        assertEquals(taskEntity.dueDate, task.dueDate)
        assertEquals(taskEntity.isCompleted, task.isCompleted)
        assertEquals(taskEntity.userId, task.userId)
    }

    @Test
    fun taskToTaskEntity() {
        val task = Task(1, "title", "description", LocalDate.now(), false, 1)
        val taskEntity = task.toTaskEntity()
        assertEquals(task.taskId, taskEntity.taskId)
        assertEquals(task.title, taskEntity.title)
        assertEquals(task.description, taskEntity.description)
        assertEquals(task.dueDate, taskEntity.dueDate)
        assertEquals(task.isCompleted, taskEntity.isCompleted)
        assertEquals(task.userId, taskEntity.userId)
    }

    @Test
    fun userEntityToUser() {
        val userEntity = UserEntity(1, "name", "username", "password")
        val user = userEntity.toUser()
        assertEquals(userEntity.userId, user.userId)
        assertEquals(userEntity.name, user.name)
        assertEquals(userEntity.username, user.username)
        assertEquals(userEntity.password, user.password)
    }

    @Test
    fun userToUserEntity() {
        val user = User(1, "name", "username", "password")
        val userEntity = user.toUserEntity()
        assertEquals(user.userId, userEntity.userId)
        assertEquals(user.name, userEntity.name)
        assertEquals(user.username, userEntity.username)
        assertEquals(user.password, userEntity.password)
    }
}