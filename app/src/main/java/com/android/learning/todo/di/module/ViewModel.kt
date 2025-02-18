package com.android.learning.todo.di.module

import com.android.learning.todo.data.room.TaskDao
import com.android.learning.todo.viewmodels.TaskViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModel {

    @Singleton
    @Provides
    fun providesViewModel(taskDao: TaskDao): TaskViewModel {
        return TaskViewModel(taskDao)
    }
}