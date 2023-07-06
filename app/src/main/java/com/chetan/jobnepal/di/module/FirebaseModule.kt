package com.chetan.jobnepal.di.module

import com.chetan.jobnepal.data.repository.authrepository.AuthRepository
import com.chetan.jobnepal.data.repository.authrepository.AuthRepositoryImpl
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
    @Provides
    fun provideAuthRepository(impl : AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideFirestoreRepository(impl: FirestoreRepositoryImpl): FirestoreRepository = impl
}