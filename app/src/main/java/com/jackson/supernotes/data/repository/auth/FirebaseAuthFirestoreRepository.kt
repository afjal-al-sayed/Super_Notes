package com.jackson.supernotes.data.repository.auth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jackson.supernotes.data.model.User
import com.jackson.supernotes.utils.constants.AuthOperationState
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseAuthFirestoreRepository {

    private val db = Firebase.firestore
    private val usersCollection = db.collection("super_notes_users")

    suspend fun saveUserData(user: User): AuthOperationState{
        val uid = user.uid
        if(uid.isNotBlank()){
            try{
                usersCollection.document(uid).set(user).await()
            } catch (e: Exception){
                return AuthOperationState.Error(error = e.localizedMessage ?: "An unexpected error occurred.")
            }
        }
        return AuthOperationState.Done
    }

    suspend fun getUserData(uid: String): AuthOperationState{
        return try{
            val user = usersCollection.document(uid).get().await().toObject(User::class.java)!!
            AuthOperationState.DoneWithResult(result = user)
        } catch (e: Exception){
            AuthOperationState.Error(error = e.localizedMessage ?: "An unexpected error occurred.")
        }
    }

}