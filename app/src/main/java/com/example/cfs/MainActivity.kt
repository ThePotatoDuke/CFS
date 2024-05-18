package com.example.cfs

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.cfs.ui.LoginViewModel
import com.example.compose.CFSTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CFSTheme {
                CFSApp(LoginViewModel())
            }
        }
    }
}

