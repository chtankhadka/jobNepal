package com.chetan.jobnepal.screens.user.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.chat.UserChatModel
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserChatViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val preference: Preference
) : ViewModel() {
    private val _state = MutableStateFlow(UserChatState())
    val state: StateFlow<UserChatState> = _state


    val onEvent: (event: UserChatEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is UserChatEvent.GetChatHistory -> {
                    _state.update {
                        it.copy(
                            videoId = event.value
                        )
                    }
                    when (val chatHistory = firestoreRepository.getUsersMsg(
                        videoId = event.value
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

                UserChatEvent.SetChatHistory -> {
                    when (firestoreRepository.setUserMsg(
                        data = UserChatModel(
                            self = true,
                            msg = state.value.userMsg,
                            msgId = (System.currentTimeMillis() / 1000L).toString(),
                            videoId = state.value.videoId,
                            userName = preference.dbTable.toString()
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
                            when (val chatHistory = firestoreRepository.getUsersMsg(
                                videoId = state.value.videoId
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

                is UserChatEvent.OnMsgChange -> {
                    _state.update {
                        it.copy(
                            userMsg = event.value
                        )
                    }
                }
            }
        }
    }
}