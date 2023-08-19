package com.chetan.jobnepal.screens.user.notification

import androidx.lifecycle.ViewModel
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val firebaseRepository: FirestoreRepository
) : ViewModel() {
}