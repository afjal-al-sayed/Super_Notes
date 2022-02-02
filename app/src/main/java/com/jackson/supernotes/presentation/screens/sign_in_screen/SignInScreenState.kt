package com.jackson.supernotes.presentation.screens.sign_in_screen

sealed class SignInScreenState{
    object Normal: SignInScreenState()
    object Loading: SignInScreenState()
}
