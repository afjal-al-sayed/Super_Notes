package com.jackson.supernotes.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jackson.supernotes.presentation.navigation.Routes
import com.jackson.supernotes.data.repository.auth.FirebaseAuthenticationRepository
import com.jackson.supernotes.utils.helpers.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: FirebaseAuthenticationRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeScreenEvents){
        when(event){
            is HomeScreenEvents.OnSignOutButtonPressed -> {
                authRepository.signOut()
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateToClearBackStack(Routes.SIGN_IN_SCREEN))
                }
            }
        }
    }

    fun getCurrentUserEmail(): String{
        return authRepository.getCurrentUser()?.email ?: "null"
    }

}