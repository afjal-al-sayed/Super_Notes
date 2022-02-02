package com.jackson.supernotes.utils.constants

sealed class OperationState{
    object Loading: OperationState()
    object Done: OperationState()
    data class Error(val error: String): OperationState()
}
