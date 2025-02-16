package com.android.learning.todo.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

   @Query("SELECT COUNT(*) > 0 FROM users WHERE username = :username")
   fun isUserExists(username: String): Boolean

    @Query("SELECT COUNT(*)>0 FROM users WHERE username = :username AND password = :password" )
    fun login(username: String,password:String): Boolean

    @Query("SELECT userId FROM users WHERE username = :username AND password = :password")
    fun getUserId(username: String,password: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

}