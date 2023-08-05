package com.chetan.jobnepal.di

import android.content.Context
import com.chetan.jobnepal.data.local.Preference
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface CustomEntryPoint {
    val preference: Preference

    companion object {
        fun getInstance(applicationContext: Context) = EntryPointAccessors.fromApplication(
            applicationContext,
            CustomEntryPoint::class.java
        )
    }
}

