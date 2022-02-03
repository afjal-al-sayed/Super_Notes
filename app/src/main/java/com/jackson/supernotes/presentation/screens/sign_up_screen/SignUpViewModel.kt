package com.jackson.supernotes.presentation.screens.sign_up_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jackson.supernotes.presentation.navigation.Routes
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInEvents
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInScreenState
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {

    var email by mutableStateOf("test@gmail.com")
        private set
    var password by mutableStateOf("123456")
        private set
    var repeatPassword by mutableStateOf("123456")
        private set
    var firstName by mutableStateOf("Joni")
        private set
    var lastName by mutableStateOf("Akbar")
        private set
    var phoneNumber by mutableStateOf("01734564826")
        private set
    var address by mutableStateOf("Dhaka-1100")
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var uiState by mutableStateOf<SignUpScreenState>(SignUpScreenState.Normal)
        private set

    fun onEvent(event: SignUpEvents){
        when (event) {
            is SignUpEvents.OnEmailChanged -> {
                email = event.email
            }
            is SignUpEvents.OnPasswordChanged -> {
                password = event.password
            }
            is SignUpEvents.OnRepeatChanged -> {
                repeatPassword = event.password
            }
            is SignUpEvents.OnFirstNameChanged -> {
                firstName = event.name
            }
            is SignUpEvents.OnLastNameChanged -> {
                lastName = event.name
            }
            is SignUpEvents.OnPhoneNumberChanged -> {
                phoneNumber = event.phone
            }
            is SignUpEvents.OnAddressChanged -> {
                address = event.address
            }
            is SignUpEvents.OnSignUpButtonPressed -> {
                viewModelScope.launch {
                    val result = validateFields()
                    if(result){
                        showSnackBarMessage("Successful")
                    }
                }
            }
            is SignUpEvents.OnSignInButtonPressed -> {
                viewModelScope.launch { sendUiEvent(UiEvent.NavigateToClearBackStack(Routes.SIGN_IN_SCREEN)) }
            }
            else -> Unit
        }
    }

    private suspend fun validateFields(): Boolean{
        if(firstName.isBlank()){
            showSnackBarMessage(emptyFieldMessage("First name"))
            return false
        }
        if(lastName.isBlank()){
            showSnackBarMessage(emptyFieldMessage("Last name"))
            return false
        }
        if(phoneNumber.length < 11){
            showSnackBarMessage("Mobile number must be at least 11 digits")
            return false
        }
        if(phoneNumber.length > 15){
            showSnackBarMessage("Mobile number can be at most 15 digits")
            return false
        }
        if(address.isBlank()){
            showSnackBarMessage(emptyFieldMessage("Address"))
            return false
        }
        if(email.isBlank()){
            showSnackBarMessage(emptyFieldMessage("E-mail"))
            return false
        }
        if(password.length < 8){
            showSnackBarMessage("Password must consist of at least 8 characters.")
            return false
        }
        if(repeatPassword != password){
            showSnackBarMessage("Password didn't match.")
            return false
        }
        return true
    }

    private suspend fun showSnackBarMessage(message: String){
        sendUiEvent(UiEvent.ShowSnackBar(message))
    }

    private suspend fun sendUiEvent(event: UiEvent){
        _uiEvent.send(event)
    }

    private fun emptyFieldMessage(name: String) = "$name field is empty."

}