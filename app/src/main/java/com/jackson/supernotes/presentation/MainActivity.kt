package com.jackson.supernotes.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jackson.supernotes.presentation.navigation.Routes
import com.jackson.supernotes.presentation.screens.home_screen.HomeScreen
import com.jackson.supernotes.data.repository.FirebaseAuthRepository
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInScreen
import com.jackson.supernotes.presentation.screens.sign_up_screen.SignUpScreen
import com.jackson.supernotes.ui.theme.SuperNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FirebaseAuthRepository()
        val isUserSignedIn = repository.isUserSignedIn()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            val navController = rememberNavController()
            SuperNotesTheme {
                NavHost(
                    navController = navController,
                    startDestination = if(isUserSignedIn) Routes.HOME_SCREEN else Routes.SIGN_UP_SCREEN
                ){
                    composable(route = Routes.SIGN_IN_SCREEN){
                        SignInScreen(navController = navController)
                    }
                    composable(route = Routes.SIGN_UP_SCREEN){
                        SignUpScreen(navController = navController)
                    }
                    composable(route = Routes.HOME_SCREEN){
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }
}