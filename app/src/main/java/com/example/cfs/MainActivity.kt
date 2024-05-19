package com.example.cfs

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.cfs.ui.LoginViewModel
import com.example.compose.CFSTheme


class MainActivity : ComponentActivity() {
    private lateinit var backPressedCallback: OnBackPressedCallback

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //do nothing
            }
        }
        // Add the callback to the onBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            CFSTheme {
                CFSApp(LoginViewModel())
            }
        }
    }


}

