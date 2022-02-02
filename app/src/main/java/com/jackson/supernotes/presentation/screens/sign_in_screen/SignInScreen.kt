package com.jackson.supernotes.presentation.screens.sign_in_screen

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jackson.supernotes.presentation.navigation.Routes
import com.jackson.supernotes.utils.constants.AuthState
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val cornerRoundness = 12.dp
    val passwordFieldFocusRequester = remember{ FocusRequester() }
    val uiState = viewModel.uiState
    val fieldState = uiState == SignInScreenState.Normal
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                    onNext = { passwordFieldFocusRequester.requestFocus() }
                ),
                enabled = fieldState
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onEvent(SignInEvents.OnPasswordChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFieldFocusRequester),
                label = {
                    Text(text = "Password")
                },
                shape = RoundedCornerShape(cornerRoundness),
                leadingIcon = { Icon(Icons.Default.Lock, "Password icon") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                enabled = fieldState
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onEvent(SignInEvents.OnSignInButtonPressed) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(cornerRoundness),
                enabled = fieldState,
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = contentColorFor(MaterialTheme.colors.primary)
                )
            ) {
                Text(
                    text = if(uiState == SignInScreenState.Loading) "Signing in" else "Sign in",
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                )
                if(uiState == SignInScreenState.Loading){
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp),
                        strokeWidth = 3.dp
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Don't have an account?"
                )
                TextButton(
                    onClick = { viewModel.onEvent(SignInEvents.OnSignUpButtonPressed) },
                    enabled = fieldState
                ) {
                    Text(
                        text = "Register here!"
                    )
                }
            }
        }
    }
}