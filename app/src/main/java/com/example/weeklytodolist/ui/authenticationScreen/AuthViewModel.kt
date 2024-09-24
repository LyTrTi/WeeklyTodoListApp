package com.example.weeklytodolist.ui.authenticationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weeklytodolist.data.firebase.fireAuth.UserRepositoryImpl
import com.example.weeklytodolist.data.firebase.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val userRepository: UserRepositoryImpl
): ViewModel() {

    var result = MutableStateFlow<Result<Boolean>>(Result.Success(false))
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.login(email, password)
                result.value = Result.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                result.value = Result.Error(e)
            }
        }
    }

    fun signUp(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.signUp(fullName, email, password)
                result.value = Result.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                result.value = Result.Error(e)
            }
        }
    }
}