package com.jackson.supernotes.utils.constants

sealed class AuthOperationState{
    object Loading: AuthOperationState()
    object Done: AuthOperationState()
    data class DoneWithResult<T>(val result: T): AuthOperationState()
    data class Error(val error: String): AuthOperationState()
}
