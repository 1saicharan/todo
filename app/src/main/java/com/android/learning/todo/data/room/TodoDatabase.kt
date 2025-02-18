package com.android.learning.todo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import javax.inject.Singleton

@Singleton
@Database(entities = [TaskEntity::class, UserEntity::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
}