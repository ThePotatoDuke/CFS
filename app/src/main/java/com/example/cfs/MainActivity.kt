package com.example.cfs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.cfs.data.supabase
import com.example.cfs.ui.LoginViewModel
import com.example.cfs.ui.theme.CFSTheme
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CFSTheme {
                CFSApp(LoginViewModel())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // it doesn't close it, but it probably shouldn't close it ?
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                supabase.auth.signOut()
            }

            val session = supabase.auth.currentSessionOrNull()
            println("nice session ${session ?: "no session"}")
            println("nice session user ${session?.user ?: "no user"}")
        }
    }
}

