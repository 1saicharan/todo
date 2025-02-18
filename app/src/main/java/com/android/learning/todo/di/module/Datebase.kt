package com.android.learning.todo.di.module

import android.content.Context
import androidx.room.Room
import com.android.learning.todo.TodoApplication
import com.android.learning.todo.data.User
import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.data.room.TodoDatabase
import com.android.learning.todo.data.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Datebase {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context : Context): TodoDatabase {
        val DATABASE_NAME = "todo_database"
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun providesUserDao(datebase: TodoDatabase):UserDao {
        return datebase.userDao()
    }

    @Singleton
    @Provides
    fun providesTaskDao(datebase: TodoDatabase): TaskDao {
        return datebase.taskDao()
    }
    
}