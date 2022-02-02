package com.jackson.supernotes.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.google.firebase.auth.FirebaseAuth
import com.jackson.supernotes.utils.constants.AuthState
import com.jackson.supernotes.utils.constants.OperationState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository {

    private val TAG = "FirebaseAuthRepository"

    private val auth = FirebaseAuth.getInstance()
    /*private val _authState = mutableStateOf<AuthState>(AuthState.SignedOut)
    val authState: State<AuthState> get() = _authState*/

    init {
        /*val isSignedIn = isUserSignedIn()
        if(isSignedIn){
            _authState.value = AuthState.SignedIn
        }else{
            _authState.value = AuthState.SignedOut
        }*/
    }

    fun isUserSignedIn(): Boolean {
        auth.currentUser ?: return false
        return true
    }

    fun getCurrentUser() = auth.currentUser

    suspend fun signIn(email: String, password: String): OperationState{
        var result: OperationState = OperationState.Loading
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.d(TAG, "SIgn up successful")
                    result = OperationState.Done
                }
                .addOnFailureListener {
                    Log.d(TAG, "sign up failed")
                    Log.d(TAG, "error: ${it.message}")
                    it.printStackTrace()
                    result = OperationState.Error(error = it.localizedMessage ?: "Unexpected error occurred")
                }
                .await()
        }catch (e: Exception){
            result = OperationState.Error(error = e.localizedMessage ?: "Unexpected error occurred")
        }
        return result
    }

    fun signOut(){
//        _authState.value = AuthState.Loading
        auth.signOut()
//        _authState.value = AuthState.SignedOut
    }

}
