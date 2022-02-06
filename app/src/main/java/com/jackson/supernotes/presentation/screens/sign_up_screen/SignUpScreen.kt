package com.jackson.supernotes.presentation.screens.sign_up_screen

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jackson.supernotes.R
import com.jackson.supernotes.presentation.components.AdditionalSignInUpButtonSection
import com.jackson.supernotes.presentation.components.SignInUpButton
import com.jackson.supernotes.presentation.components.SignUpInputField
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInEvents
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInScreenState
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
    val focusManager = LocalFocusManager.current
    val bannerImage = painterResource(id = R.drawable.ic_sign_up_banner)
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val firstFieldIndex = 2;

    LaunchedEffect(true){
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToClearBackStack -> {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    navController.navigate(event.route){
                        popUpTo(0)
                    }
                }
                is UiEvent.NavigateTo -> {
                    focusManager.clearFocus()
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item{
                Image(
                    painter = bannerImage,
                    contentDescription = "Sign up banner image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp, vertical = 16.dp)
                )
                Text(
                    text = "Create New Account",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Please fill up the following form and click \"Create account\" to continue",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),

                )
            }
            SignUpInputField(
                value = viewModel.firstName,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnFirstNameChanged(it)) },
                fieldOrder = firstFieldIndex,
                label = "First name",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager,
            )
            SignUpInputField(
                value = viewModel.lastName,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnLastNameChanged(it)) },
                fieldOrder = firstFieldIndex + 1,
                label = "Last name",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager,
            )
            SignUpInputField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnPhoneNumberChanged(it)) },
                fieldOrder = firstFieldIndex + 2,
                label = "Mobile number",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager,
                keyboardType = KeyboardType.Phone
            )
            SignUpInputField(
                value = viewModel.address,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnAddressChanged(it)) },
                fieldOrder = firstFieldIndex + 3,
                label = "Address",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager
            )
            SignUpInputField(
                value = viewModel.email,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnEmailChanged(it)) },
                fieldOrder = firstFieldIndex + 4,
                label = "E-mail",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager,
                keyboardType = KeyboardType.Email
            )
            SignUpInputField(
                value = viewModel.password,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnPasswordChanged(it)) },
                fieldOrder = firstFieldIndex + 5,
                label = "Password",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager,
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            SignUpInputField(
                value = viewModel.repeatPassword,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnRepeatChanged(it)) },
                fieldOrder = firstFieldIndex + 6,
                label = "Repeat Password",
                cornerRoundness = cornerRoundness,
                isEnabled = fieldState,
                coroutineScope = coroutineScope,
                listState = listState,
                focusManager = focusManager,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                imeAction = ImeAction.Done
            )
            item{
                Spacer(Modifier.height(8.dp))
                SignInUpButton(
                    normalText = "Create account",
                    loadingText = "Signing up",
                    isEnabled = fieldState,
                    isLoading = uiState == SignUpScreenState.Loading,
                    onClick = { viewModel.onEvent(SignUpEvents.OnSignUpButtonPressed) },
                    cornerRoundness = cornerRoundness
                )
            }
            item{
                AdditionalSignInUpButtonSection(
                    message = "Already registered?",
                    buttonText = "Sign in instead",
                    onClick = { viewModel.onEvent(SignUpEvents.OnSignInButtonPressed) },
                    isEnabled = fieldState
                )
                Spacer(Modifier.height(196.dp))
            }
        }
    }
}