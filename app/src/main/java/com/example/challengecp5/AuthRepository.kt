package com.example.challengecp5

import android.content.Context
import kotlinx.coroutines.coroutineScope

class AuthRepository (context: Context) {
    val myDatabase = MyDatabase.getInstance(context)
    suspend fun login(username: String, password: String): User? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.login(username, password)
    }
    suspend fun insertUser(user: User):Long? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.insertUser(user)
    }
}