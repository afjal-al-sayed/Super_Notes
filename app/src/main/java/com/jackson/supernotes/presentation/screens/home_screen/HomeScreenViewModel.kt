package com.jackson.supernotes.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jackson.supernotes.presentation.navigation.Routes
import com.jackson.supernotes.repository.FirebaseAuthRepository
import com.jackson.supernotes.utils.helpers.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {

    val authRepository = FirebaseAuthRepository()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeScreenEvents){
        when(event){
            is HomeScreenEvents.OnSignOutButtonPressed -> {
                authRepository.signOut()
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateTo(Routes.SIGN_IN_SCREEN))
                }
            }
        }
    }

    fun getCurrentUserEmail(): String{
        return authRepository.getCurrentUser()?.email ?: "null"
    }

}