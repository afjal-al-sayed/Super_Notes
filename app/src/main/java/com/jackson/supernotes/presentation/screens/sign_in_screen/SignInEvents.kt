package com.jackson.supernotes.presentation.screens.sign_in_screen

sealed class SignInEvents {
    data class OnEmailChanged(val email: String): SignInEvents()
    data class OnPasswordChanged(val password: String): SignInEvents()
    object OnSignInButtonPressed: SignInEvents()
}