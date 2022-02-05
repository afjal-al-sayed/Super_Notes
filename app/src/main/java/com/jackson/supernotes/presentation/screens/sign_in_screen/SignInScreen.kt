package com.jackson.supernotes.presentation.screens.sign_in_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jackson.supernotes.R
import com.jackson.supernotes.presentation.components.AdditionalSignInUpButtonSection
import com.jackson.supernotes.presentation.components.SignInUpButton
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val cornerRoundness = 12.dp
    val focusManager = LocalFocusManager.current
    val uiState = viewModel.uiState
    val fieldState = uiState == SignInScreenState.Normal
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val logoPainter = painterResource(id = R.drawable.ic_logo_main)

    LaunchedEffect(true){
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToClearBackStack -> {
                    keyboardController?.hide()
                    navController.navigate(event.route){
                        popUpTo(0)
                    }
                }
                is UiEvent.NavigateTo -> {
                    keyboardController?.hide()
                    navController.navigate(event.route)
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Super Notes - Sign in")
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = logoPainter,
                contentDescription = "Super notes logo",
                modifier = Modifier
                    .size(164.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEvent(SignInEvents.OnEmailChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "E-mail")
                },
                shape = RoundedCornerShape(cornerRoundness),
                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                enabled = fieldState
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onEvent(SignInEvents.OnPasswordChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Password")
                },
                shape = RoundedCornerShape(cornerRoundness),
                leadingIcon = { Icon(Icons.Default.Lock, "Password icon") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                enabled = fieldState
            )
            Spacer(Modifier.height(20.dp))
            SignInUpButton(
                normalText = "Sign in",
                loadingText = "Signing in",
                isEnabled = fieldState,
                isLoading = uiState == SignInScreenState.Loading,
                onClick = { viewModel.onEvent(SignInEvents.OnSignInButtonPressed) },
                cornerRoundness = cornerRoundness
            )
            Spacer(Modifier.height(8.dp))
            AdditionalSignInUpButtonSection(
                message = "Don't have an account?",
                buttonText = "Register here!",
                onClick = { viewModel.onEvent(SignInEvents.OnSignUpButtonPressed) },
                isEnabled = fieldState
            )
            Spacer(Modifier.height(96.dp))
        }
    }
}