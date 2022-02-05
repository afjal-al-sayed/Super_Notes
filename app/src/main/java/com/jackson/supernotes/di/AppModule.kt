package com.jackson.supernotes.di

import com.jackson.supernotes.data.repository.auth.FirebaseAuthFirestoreRepository
import com.jackson.supernotes.data.repository.auth.FirebaseAuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthFirestoreRepository() = FirebaseAuthFirestoreRepository()

    @Provides
    @Singleton
    fun provideFirebaseAuthenticationRepository(
        firebaseAuthFirestoreRepository: FirebaseAuthFirestoreRepository
    ) = FirebaseAuthenticationRepository(firebaseAuthFirestoreRepository)

}