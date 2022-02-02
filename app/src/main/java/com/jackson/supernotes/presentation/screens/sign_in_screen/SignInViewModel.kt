package com.jackson.supernotes.presentation.screens.sign_in_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jackson.supernotes.presentation.navigation.Routes
import com.jackson.supernotes.repository.FirebaseAuthRepository
import com.jackson.supernotes.utils.constants.OperationState
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.math.sign

class SignInViewModel: ViewModel() {

    var email by mutableStateOf("test@gmail.com")
        private set
    var password by mutableStateOf("123456")
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var uiState by mutableStateOf<SignInScreenState>(SignInScreenState.Normal)
        private set

    private val authRepository = FirebaseAuthRepository()

    fun onEvent(event: SignInEvents){
        when (event) {
            is SignInEvents.OnEmailChanged -> {
                email = event.email
            }
            is SignInEvents.OnPasswordChanged -> {
                password = event.password
            }
            is SignInEvents.OnSignInButtonPressed -> {
                viewModelScope.launch {
                    if(email.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackBar(message = "Email field is empty"))
                        return@launch
                    }
                    if(password.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackBar(message = "Password field is empty"))
                        return@launch
                    }
                    signIn()
                }
            }
        }
    }

    private suspend fun sendUiEvent(event: UiEvent){
        _uiEvent.send(event)
    }

    private suspend fun signIn(){
        uiState = SignInScreenState.Loading
        val result = authRepository.signIn(email, password)
        when(result){
            is OperationState.Done -> {
                sendUiEvent(UiEvent.NavigateTo(Routes.HOME_SCREEN))
            }
            is OperationState.Error -> {
                sendUiEvent(UiEvent.ShowSnackBar(message = result.error))
            }
            else -> Unit
        }
        uiState = SignInScreenState.Normal
    }
}