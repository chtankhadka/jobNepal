package com.chetan.jobnepal.screens.admin.uploadvideo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import com.chetan.jobnepal.utils.GenerateRandomNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class UploadVideoViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UploadVideoState())
    val state: StateFlow<UploadVideoState> = _state

    init {
        _state.update {
            it.copy(
                publishedTime = LocalDateTime.now().toString()
            )
        }

    }

    val onEvent: (event: UploadVideoEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is UploadVideoEvent.UploadVideoUrl -> {
                    val state = state.value
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                description = "Uploading...",
                                isCancellable = false,
                                yesNoRequired = false
                            )
                        )
                    }
                    state.videoList.let {
                        _state.update {
                            it.copy(progress = Progress(value = 0.0F))
                        }
                        val resource = repository.uploadNewVideoLink(
                            UploadNewVideoLink(
                                id = state.id + GenerateRandomNumber.generateRandomNumber(
                                    111111..999999
                                ),
                                title = state.title,
                                description = state.description,
                                videoLink = state.url,
                                publishedTime = state.publishedTime,
                                province = state.editProvince
                            )

                        )
                        when (resource) {
                            is Resource.Failure -> {
                                _state.update {
                                    it.copy(
                                        infoMsg = Message.Error(description = resource.exception.message),
                                        progress = null
                                    )
                                }
                            }

                            Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        infoMsg = null,
                                        editProvince = "",
                                        id = "",
                                        url = "",
                                        title = "",
                                        description = "",
                                        publishedTime = "",
                                        videoList = emptyList()
                                    )
                                }
                            }
                        }
                    }
                }

                is UploadVideoEvent.IdChange -> {
                    _state.update {
                        it.copy(
                            id = event.value
                        )

                    }
                }

                is UploadVideoEvent.UrlChange -> {
                    _state.update {
                        it.copy(url = event.value)
                    }
                }

                is UploadVideoEvent.DescriptionChange -> {
                    _state.update {
                        it.copy(description = event.value)
                    }
                }

                is UploadVideoEvent.TitleChange -> {
                    _state.update {
                        it.copy(title = event.value)
                    }
                }

                UploadVideoEvent.DismissInfoMsg -> {
                    _state.update {
                        it.copy(infoMsg = null)
                    }
                }

                is UploadVideoEvent.OnSelectProvince -> {
                    _state.update {
                        it.copy(
                            editProvince = event.value
                        )
                    }
                }
            }
        }

    }
}
