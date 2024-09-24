package com.example.weeklytodolist.data.firebase.fireAuth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.weeklytodolist.data.firebase.Result
import com.example.weeklytodolist.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
): UserRepository {

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun signUp(fullName: String, email: String, password: String): Result<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            saveUserToFirestore(user = User(fullName, email, password))
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveUserToFirestore(user: User) {
        fireStore.collection("users").document(user.email).set(user).await()
    }

}