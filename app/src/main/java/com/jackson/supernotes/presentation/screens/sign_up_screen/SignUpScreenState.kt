package com.jackson.supernotes.presentation.screens.sign_up_screen

sealed class SignUpScreenState{
    object Normal: SignUpScreenState()
    object Loading: SignUpScreenState()
}
