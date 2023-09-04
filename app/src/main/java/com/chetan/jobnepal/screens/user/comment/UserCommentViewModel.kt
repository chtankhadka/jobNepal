package com.chetan.jobnepal.screens.user.comment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.comment.UserCommentModel
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class UserCommentViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val preference: Preference
) : ViewModel() {
    private val _state = MutableStateFlow(UserCommentState())
    val state: StateFlow<UserCommentState> = _state


    @RequiresApi(Build.VERSION_CODES.O)
    val onEvent: (event: UserCommentEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is UserCommentEvent.GetUsersComment -> {
                    when (val getUserComment = firestoreRepository.getUsersComment(event.value)) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    userCommentList = getUserComment.data,
                                    videoId = event.value
                                )
                            }
                        }
                    }
                }

                UserCommentEvent.SetUserComment -> {
                    when (firestoreRepository.setUserComment(
                        data = UserCommentModel(
                            isSelf = true,
                            comment = state.value.userComment,
                            commentId = (System.currentTimeMillis() / 1000).toString(),
                            videoId = state.value.videoId,
                            userName = preference.gmailUserName?:"Unknown User"

                        )
                    )) {
                        is Resource.Failure -> {
                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            when (val getUserComment =
                                firestoreRepository.getUsersComment(state.value.videoId)) {
                                is Resource.Failure -> {

                                }

                                Resource.Loading -> {

                                }

                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            userCommentList = getUserComment.data.toMutableList(),
                                            userComment = ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is UserCommentEvent.OnCommentChange -> {
                    _state.update {
                        it.copy(
                            userComment = event.value
                        )
                    }
                }
            }
        }
    }
}
