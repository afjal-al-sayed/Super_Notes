package com.jackson.supernotes.presentation.screens.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.jackson.supernotes.repository.FirebaseAuthRepository
import com.jackson.supernotes.utils.constants.AuthState
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {

    LaunchedEffect(true){
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateTo -> {
                    navController.navigate(event.route){
                        popUpTo(0)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Super Notes")
                }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            val email = viewModel.getCurrentUserEmail()
            Text(text = "signed in as $email")
            Spacer(Modifier.height(8.dp))
            Button(onClick = { viewModel.onEvent(HomeScreenEvents.OnSignOutButtonPressed) }) {
                Text(text = "Sign Out")
            }
        }
    }
}