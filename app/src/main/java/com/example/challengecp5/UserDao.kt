package com.example.challengecp5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE username =:username AND password = :password")
    fun login(username: String, password: String):User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Long

}