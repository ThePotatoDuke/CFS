package com.example.cfs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfs.data.LoginUiState
import com.example.cfs.data.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.ktor.utils.io.printStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private var isSignedIn: Boolean = false

    var userMail by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set

    fun updateUserName(username: String) {
        this.userMail = username
    }

    fun updatePassword(password: String) {
        this.userPassword = password
    }

    fun signInWithEmail() {
        viewModelScope.launch {
            try {
                val yes = supabase.auth.signInWith(Email) {
                    email = userMail
                    password = userPassword
                }
                println("yes: $yes")
                val user = supabase.auth.retrieveUserForCurrentSession(updateSession = true)
                println("user: $user")

            } catch (e: Exception) {
                println(e.printStack())
                // TODO we'll manage the session
                var session = supabase.auth.currentSessionOrNull()
                println("nice session ${session ?: "no session"}")
                println("nice session user ${session?.user ?: "no user"}")
                supabase.auth.signOut()
                println("signing out...")

                session = supabase.auth.currentSessionOrNull()
                println("nice session ${session ?: "no session"}")
                println("nice session user ${session?.user ?: "no user"}")
            }
        }
    }

    fun isSignedIn(): Boolean {
        //
        return isSignedIn
    }

}