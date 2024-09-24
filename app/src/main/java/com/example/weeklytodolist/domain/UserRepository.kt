package com.example.weeklytodolist.domain

import com.example.weeklytodolist.data.firebase.Result
import com.example.weeklytodolist.data.firebase.fireAuth.User

interface UserRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun signUp(fullName: String, email: String, password: String): Result<Boolean>
    suspend fun saveUserToFirestore(user: User)
}