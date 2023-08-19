package com.chetan.jobnepal.di

import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@EntryPoint
interface HiltEntryPoint {
    fun firestoreRepository() : FirestoreRepository
}