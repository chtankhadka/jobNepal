package com.chetan.jobnepal.screens.user.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNotificationViewModel @Inject constructor(
    private val firebaseRepository: FirestoreRepository,
    preference: Preference
) : ViewModel() {
    private val _state = MutableStateFlow(UserNotificationState())
    val state: StateFlow<UserNotificationState> = _state
 init {
     preference.isRingBell = false
     getNotification()
 }

    val onEvent : (event: UserNotificationEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {

                is UserNotificationEvent.readState -> {
                    val item =
                        state.value.userNotificationResponse.find { it.time == event.value }
                    val changeReadState = item?.let {
                        firebaseRepository.saveNotification(
                            StoreNotificationRequestResponse(
                                body = it.body,
                                time = it.time,
                                readNotice = true
                            )
                        )
                    }
                    when (changeReadState){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            getNotification()
                        }

                        else -> {}
                    }
                }
            }
        }

    }
    fun getNotification(){
        viewModelScope.launch {
            val getResponse = firebaseRepository.getNotification()
            when (getResponse){
                is Resource.Failure -> {

                }
                Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            infoMsg = null,
                            userNotificationResponse = getResponse.data

                        )
                    }
                }
            }
        }
    }
}