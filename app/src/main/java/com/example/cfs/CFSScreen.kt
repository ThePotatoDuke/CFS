package com.example.cfs

import MainScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cfs.ui.LoginScreen
import com.example.cfs.ui.LoginViewModel


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
    NavHost(
        navController = navController,
        startDestination = CFSScreens.Login.name,
    ) {
        composable(route = CFSScreens.Login.name) {
            LoginScreen(
                userName = viewModel.userName,
                password = viewModel.password,
                onUserNameEdit = { viewModel.updateUserName(it) },
                onPasswordEdit = { viewModel.updatePassword(it) },
                isLoginError = uiState.isLoginError,
                onLoginButtonClicked = {
                    if (viewModel.checkUser()) {
                        navController.navigate(CFSScreens.FeedBackRequest.name)
                    }

                }
            )


        }
        composable(route = CFSScreens.FeedBackRequest.name) {
            MainScreen(
            )
        }
    }
}
