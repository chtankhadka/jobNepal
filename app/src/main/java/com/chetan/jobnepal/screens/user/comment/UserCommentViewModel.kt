package com.chetan.jobnepal.screens.user.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.comment.UserCommentModel
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserCommentViewModel @Inject constructor(
        private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UserCommentState())
    val state : StateFlow<UserCommentState> = _state

    init {


    }

    val onEvent : (event : UserCommentEvent) -> Unit  = { event ->
        viewModelScope.launch {
            when (event){
                is UserCommentEvent.GetUsersComment -> {
                    when(val getUserComment = firestoreRepository.getUsersComment(event.value)){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    userCommentList = getUserComment.data
                                )
                            }
                        }
                    }
                }

                UserCommentEvent.SetUserComment -> {
                    when(firestoreRepository.setUserComment(data = UserCommentModel())){
                        is Resource.Failure -> {
                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {

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