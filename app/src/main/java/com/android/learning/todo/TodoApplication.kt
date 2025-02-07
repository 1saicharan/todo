package com.android.learning.todo

import android.app.Application
import android.content.Context
import com.android.learning.todo.data.room.TodoDatabase


class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
        private set
    }
}