package com.jackson.supernotes.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.jackson.supernotes.data.model.User
import com.jackson.supernotes.utils.constants.AuthOperationState
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository {

    private val TAG = "FirebaseAuthRepository"

    private val auth = FirebaseAuth.getInstance()
    private val firestoreRepository = FirebaseFirestoreRepository()
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

    suspend fun signIn(email: String, password: String): AuthOperationState{
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthOperationState.Done
        }catch (e: Exception){
            AuthOperationState.Error(error = e.localizedMessage ?: "Unexpected error occurred")
        }
    }

    suspend fun signUp(user: User): AuthOperationState{
        return try{
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()!!
            val createdUser = result.user!!
            val userWithUid = user.copy(uid = createdUser.uid)
            val finalResult = firestoreRepository.saveUserData(userWithUid)
            finalResult
        } catch (e: Exception){
            AuthOperationState.Error(e.localizedMessage ?: "Unexpected error occurred")
        }

    }

    fun signOut(){
//        _authState.value = AuthState.Loading
        auth.signOut()
//        _authState.value = AuthState.SignedOut
    }

}
