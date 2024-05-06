package com.example.cfs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cfs.ui.LoginViewModel
import com.example.cfs.ui.theme.CFSTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CFSTheme {
                CFSApp(LoginViewModel())
            }
        }
    }
}

