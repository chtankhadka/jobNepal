package com.chetan.jobnepal.screens.user.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.screens.user.academic.AcademicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNotificationViewModel @Inject constructor(
    private val firebaseRepository: FirestoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserNotificationState())
    val state: StateFlow<UserNotificationState> = _state
 init {
     getNotification()
 }

    val onEvent : (event: UserNotificationEvent) -> Unit = { event ->
        when (event){
            UserNotificationEvent.test -> {


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