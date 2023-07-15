package com.chetan.jobnepal.di.module

import android.app.Application
import android.content.Context
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.repository.authrepository.AuthRepository
import com.chetan.jobnepal.data.repository.authrepository.AuthRepositoryImpl
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepositoryImpl
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun providePreference(application: Application): Preference {
        return Preference(application)
    }
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
    @Singleton
    fun provideFirebaseStorageInstance() : FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun provideStorageReference(storage: FirebaseStorage) : StorageReference = storage.reference

    @Provides
    @Singleton
    fun provideFirebaseStorageRepository(
        storage: FirebaseStorage,
        storageRef: StorageReference,
        preference: Preference
    ) : FirebaseStorageRepository = FirebaseStorageRepositoryImpl(storage,storageRef,preference)


    @Provides
    fun provideAuthRepository(impl : AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideFirestoreRepository(
       firestore : FirebaseFirestore,
       preference: Preference
    ): FirestoreRepository = FirestoreRepositoryImpl(firestore, preference)
}