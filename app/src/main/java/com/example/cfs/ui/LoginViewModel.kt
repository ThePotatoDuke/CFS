package com.example.cfs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cfs.data.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var userName by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateUserName(username: String) {
        this.userName = username
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun checkUser(): Boolean {
        if (userName.equals("deez") && password.equals("nutz")) {

            _uiState.update { currentState ->
                currentState.copy(isLoginError = false)
            }
            return true

        } else {
            _uiState.update { currentState ->
                currentState.copy(isLoginError = true)
            }
            return false

        }
    }

}