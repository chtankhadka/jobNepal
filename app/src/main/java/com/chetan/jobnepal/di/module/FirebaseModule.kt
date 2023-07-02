package com.chetan.jobnepal.di.module

import com.chetan.jobnepal.data.authrepository.AuthRepository
import com.chetan.jobnepal.data.authrepository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
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
    fun provideAuthRepository(impl : AuthRepositoryImpl): AuthRepository = impl
}