package com.jackson.supernotes.presentation.screens.sign_up_screen

sealed class SignUpEvents {
    data class OnEmailChanged(val email: String): SignUpEvents()
    data class OnPasswordChanged(val password: String): SignUpEvents()
    data class OnRepeatChanged(val password: String): SignUpEvents()
    data class OnFirstNameChanged(val name: String): SignUpEvents()
    data class OnLastNameChanged(val name: String): SignUpEvents()
    data class OnPhoneNumberChanged(val phone: String): SignUpEvents()
    data class OnAddressChanged(val address: String): SignUpEvents()
    object OnSignUpButtonPressed: SignUpEvents()
    object OnSignInButtonPressed: SignUpEvents()
}