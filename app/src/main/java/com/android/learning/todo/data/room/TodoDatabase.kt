package com.android.learning.todo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskEntity::class, UserEntity::class], version = 1)
//@TypeConverters(DateConverter::class)
abstract class TodoDatabase: RoomDatabase() {

    companion object{
        const val DATABASE_NAME = "todo_database"

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    DATABASE_NAME
                )
//                    .addTypeConverter(DateConverter)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
}