package com.jackson.supernotes.utils.constants

sealed class AuthState{
    object Loading: AuthState()
    object SignedIn: AuthState()
    object SignedOut: AuthState()
}
