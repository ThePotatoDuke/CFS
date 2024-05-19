package com.example.cfs

import MainScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cfs.ui.LoginScreen
import com.example.cfs.ui.LoginViewModel
import kotlin.math.sign


enum class CFSScreens() {
    Login,
    FeedBackRequest

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CFSApp(
    viewModel: LoginViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    var yes by remember { mutableStateOf(false) }
    val data = uiState.sessionPersist

    LaunchedEffect(key1 = data) {
        yes = viewModel.checkUserSession()
    }

    if (yes) {
        println("cfs navigation")
        navController.navigate(CFSScreens.FeedBackRequest.name)
    } else {
        println("hello from cfs")
    }


    NavHost(
        navController = navController,
        startDestination = CFSScreens.Login.name,
    ) {
        composable(route = CFSScreens.Login.name) {
            LoginScreen(
                userName = viewModel.userMail,
                password = viewModel.userPassword,
                onUserNameEdit = { viewModel.updateUserName(it) },
                onPasswordEdit = { viewModel.updatePassword(it) },
                isLoginError = uiState.isLoginError,
                onLoginButtonClicked = {
                    viewModel.signInUser { signedIn ->
                        if (signedIn) {
                            navController.navigate(CFSScreens.FeedBackRequest.name)
                        }
                    }
                }
            )


        }
        composable(route = CFSScreens.FeedBackRequest.name) {
            MainScreen(
                onExitClick = {
                    viewModel.signOutUser {signedOut ->
                        if (signedOut) {
                            navController.navigate(CFSScreens.Login.name)
                        }
                    }
                } //add logout stuff here!!!!
            )
        }
    }
}
