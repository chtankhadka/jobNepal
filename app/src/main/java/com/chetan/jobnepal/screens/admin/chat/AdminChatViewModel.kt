package com.chetan.jobnepal.screens.admin.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.chat.UserChatModel
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminChatViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AdminChatState())
    val state: StateFlow<AdminChatState> = _state

    init {
        viewModelScope.launch {
            when (val resource = firestoreRepository.getVideoIdList()) {
                is Resource.Failure -> {}
                Resource.Loading -> {

                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            videoIdList = resource.data
                        )
                    }
                }
            }
        }
    }

    val onEvent: (event: AdminChatEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                AdminChatEvent.DismissInfoMsg -> {

                }

                is AdminChatEvent.GetChatRequestList -> {
                    when (val usersList = firestoreRepository.getChatRequestUsers(event.id)) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    chatUsersList = usersList.data,
                                    vId = event.id
                                )
                            }
                        }
                    }
                }

                is AdminChatEvent.GetChatHistory -> {
                    when(val msg = firestoreRepository.getUsersAdminMsg(state.value.vId,event.userName)){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    userChatHistory = msg.data,
                                    user = event.userName
                                )
                            }
                        }
                    }
                }

                is AdminChatEvent.OnMsgChange -> {
                    _state.update {
                        it.copy(
                            userMsg = event.value
                        )
                    }

                }

                AdminChatEvent.SetChatHistory -> {
                    when (firestoreRepository.setUserMsg(
                        data = UserChatModel(
                            self = true,
                            msg = state.value.userMsg,
                            msgId = System.currentTimeMillis().toString(),
                            videoId = state.value.vId,
                            userName = state.value.user
                        )
                    )) {
                        is Resource.Failure -> {}
                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    userMsg = ""
                                )
                            }
                            when (val chatHistory = firestoreRepository.getUsersAdminMsg(
                                videoId = state.value.vId,state.value.user
                            )) {
                                is Resource.Failure -> {

                                }

                                Resource.Loading -> {

                                }

                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            userChatHistory = chatHistory.data
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}