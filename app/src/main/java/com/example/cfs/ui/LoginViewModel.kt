package com.example.cfs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfs.data.LoginUiState
import com.example.cfs.data.supabase
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var userEmail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set
    var sessionPersist by mutableStateOf(false)
        private set

    fun updateUserName(username: String) {
        this.userEmail = username
    }

    fun updatePassword(password: String) {
        this.userPassword = password
    }

    fun updateSession(yes: Boolean) {
        this.sessionPersist = yes
    }

    init {
        println("hellow orld")
//        viewModelScope.launch {
//            supabase.auth.signOut()
//        }
        foo()
    }

    fun checkUser(): Boolean {
        if (userEmail == "deez" && userPassword == "nutz") {
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

    fun foo() {
        viewModelScope.launch {
            supabase.auth.sessionStatus.collect {
                when (it) {
                    is SessionStatus.Authenticated -> {
                        println("Received new authenticated session.")
                        when (it.source) { //Check the source of the session
                            SessionSource.External -> {
                                println("external")
                            }

                            is SessionSource.Refresh -> {
                                println("refresh")
                            }

                            is SessionSource.SignIn -> {
                                println("user signed in")
                                updateSession(true)
                            }

                            is SessionSource.SignUp -> {
                                println("sign up")
                            }

                            SessionSource.Storage -> {
                                println("storage")
                            }

                            SessionSource.Unknown -> {
                                println("unknown")
                            }

                            is SessionSource.UserChanged -> {
                                println("user changed")
                            }

                            is SessionSource.UserIdentitiesChanged -> {
                                println("id changed")
                            }

                            SessionSource.AnonymousSignIn -> {
                                println("anon sign in")
                            }
                        }
                    }

                    SessionStatus.LoadingFromStorage -> println("Loading from storage")
                    SessionStatus.NetworkError -> println("Network error")
                    is SessionStatus.NotAuthenticated -> {
                        if (it.isSignOut) {
                            println("User signed out")
                            updateSession(false)
                        } else {
                            println("User not signed in")
                        }
                    }
                }
            }
        }
    }

    suspend fun checkUserSession(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val user = supabase.auth.currentSessionOrNull()?.user
                println("session exists for user: $user")
                updateSession(true)
                user != null
            } catch (e: Exception) {
                println("session does not exist")
                updateSession(false)
                false
            }

        }
    }

    fun signInUser(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    supabase.auth.signInWith(Email) {
                        email = userEmail
                        password = userPassword
                    }
                    val user = supabase.auth.currentSessionOrNull()?.user
                    println("user signed in: ${user?.email}")
                    updateSession(true)
                    true
                } catch (e: Exception) {
                    println("user couldn't sign in: ${e.message}")
                    updateSession(false)
                    false
                }
            }
            onResult(result)
        }
    }


}