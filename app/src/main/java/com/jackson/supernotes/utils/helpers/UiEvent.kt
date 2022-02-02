package com.jackson.supernotes.utils.helpers

sealed class UiEvent{
    data class NavigateTo(val route: String): UiEvent()
    data class ShowSnackBar(val message: String): UiEvent()
}
