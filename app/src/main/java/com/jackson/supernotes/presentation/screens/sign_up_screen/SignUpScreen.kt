package com.jackson.supernotes.presentation.screens.sign_up_screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInEvents
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInScreenState
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val cornerRoundness = 12.dp
    val uiState = viewModel.uiState
    val fieldState = uiState == SignUpScreenState.Normal
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
                    Text(text = "Super Notes - Sign Up")
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                OutlinedTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnFirstNameChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "First name")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    enabled = fieldState
                )
            }
            item {
                OutlinedTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnLastNameChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Last name")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    enabled = fieldState
                )
            }
            item {
                OutlinedTextField(
                    value = viewModel.phoneNumber,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnPhoneNumberChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Mobile number")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    enabled = fieldState
                )
            }
            item {
                OutlinedTextField(
                    value = viewModel.address,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnAddressChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Address")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    enabled = fieldState
                )
            }
            item {
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnEmailChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "E-mail")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    enabled = fieldState,
                )

            }
            item {
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnPasswordChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Password")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    enabled = fieldState
                )
            }
            item {
                OutlinedTextField(
                    value = viewModel.repeatPassword,
                    onValueChange = { viewModel.onEvent(SignUpEvents.OnRepeatChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Repeat Password")
                    },
                    shape = RoundedCornerShape(cornerRoundness),
                    //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    enabled = fieldState
                )
            }
            item{
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.onEvent(SignUpEvents.OnSignUpButtonPressed) },
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
                        text = if(uiState == SignUpScreenState.Loading) "Signing up" else "Sign Up",
                        modifier = Modifier
                            .padding(vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 18.sp
                        ),
                    )
                    if(uiState == SignUpScreenState.Loading){
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
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Already registered?"
                    )
                    TextButton(
                        onClick = { viewModel.onEvent(SignUpEvents.OnSignInButtonPressed) },
                        enabled = fieldState
                    ) {
                        Text(
                            text = "Sign in instead"
                        )
                    }
                }
            }
        }
    }
}