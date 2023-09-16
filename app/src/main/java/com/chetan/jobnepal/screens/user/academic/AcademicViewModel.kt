package com.chetan.jobnepal.screens.user.academic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.House
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.academic.UploadAcademicData
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import com.chetan.jobnepal.utils.StringValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AcademicViewModel @Inject constructor(
    private val storageRepository: FirebaseStorageRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AcademicState())
    val state: StateFlow<AcademicState> = _state

    @RequiresApi(Build.VERSION_CODES.O)
    val onEvent: (event: AcademicEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is AcademicEvent.UploadAttachement -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                description = "Uploading your data...",
                                isCancellable = false,
                                yesNoRequired = false
                            )
                        )
                    }
                    val state = state.value
                    state.uploadAttachementList.let {
                        _state.update {
                            it.copy(progress = Progress(value = 0.0F))
                        }
                        for (uri in event.value) {
                            val resource = storageRepository.uploadAcademicAttachement(
                                uri, state.selectedLevel
                            )
                            when (resource) {
                                is Resource.Failure -> {
                                    _state.update {
                                        it.copy(
                                            infoMsg = Message.Error(
                                                description = resource.exception.message,
                                                yesNoRequired = true
                                            ),
                                            progress = null
                                        )
                                    }
                                }

                                is Resource.Loading -> TODO()
                                is Resource.Success -> {
                                    //academic data
                                    if (resource.data.second.isNotEmpty()) {
                                        when (firestoreRepository.uploadAcademicData(
                                            data =
                                            UploadAcademicData(
                                                id = resource.data.first,
                                                level = state.selectedLevel,
                                                url = resource.data.second,
                                                date = LocalDateTime.now().toString()
                                            )
                                        )) {
                                            is Resource.Failure -> {
                                                _state.update {
                                                    it.copy(
                                                        infoMsg = Message.Error(
                                                            description = "Data Not uploaded",
                                                            yesNoRequired = true
                                                        ),
                                                        progress = null
                                                    )
                                                }
                                            }

                                            Resource.Loading -> TODO()
                                            is Resource.Success -> {
                                                _state.update {
                                                    it.copy(
                                                        infoMsg = null,
                                                        progress = null,
                                                        showDialog = false,
                                                        selectedLevel = "",
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

                is AcademicEvent.LevelSelected -> {
                    _state.update {
                        it.copy(selectedLevel = event.value)
                    }
                }

                is AcademicEvent.ShowDialog -> {
                    _state.update {
                        it.copy(
                            showDialog = event.value
                        )
                    }
                }

                AcademicEvent.Delete -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(description = "deleting...", yesNoRequired = false)
                        )
                    }
                    val resource = storageRepository.deleteAcademicAtachements(
                        state.value.selectedLevel,
                        state.value.academicListResponse.map {
                            it.id
                        })
                    when (resource) {
                        is Resource.Failure -> {
                            _state.update {
                                it.copy(
                                    infoMsg = Message.Error(description = resource.exception.message),
                                    progress = null
                                )
                            }
                        }

                        is Resource.Loading -> {}
                        is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        academicListResponse = emptyList()
                                    )
                                }
                        }
                    }

                    val resource1 =
                        firestoreRepository.deleteAcademicData(level = state.value.selectedLevel)
                    when (resource1) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    selectedLevel = "",
                                    infoMsg = null
                                )
                            }
                        }
                    }

                }

                is AcademicEvent.ShowEdit -> {
                    _state.update {
                        it.copy(showEdit = event.value)
                    }
                }

                AcademicEvent.DismissInfoMsg -> {
                    _state.update {
                        it.copy(infoMsg = null)
                    }
                }

                is AcademicEvent.GetAcademicEvent -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                lottieImage = R.raw.loading,
                                description = "Loading your data",
                                isCancellable = false,
                                yesNoRequired = false
                            ),
                            selectedLevel = event.value
                        )
                    }
                    val state = state.value
                    state.academicListResponse.let {
                        _state.update {
                            it.copy(progress = Progress(value = 0.0F))
                        }
                        val resource = firestoreRepository.getAcademicData(event.value)
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
                                _state.update {
                                    it.copy(
                                        infoMsg = Message.Loading(
                                            image = Icons.Default.House,
                                            title = StringValue.DynamicString("Loading"),
                                            description = "just for test"
                                        )
                                    )
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        progress = null,
                                        academicListResponse = resource.data,
                                        infoMsg = null
                                    )

                                }
                            }
                        }
                    }
                }

                is AcademicEvent.DeleteSelectedItem -> {
                    _state.update {
                        it.copy(
                            academicListResponse = state.value.academicListResponse.filter { it.id != event.value.id }
                        )
                    }
                    when (storageRepository.deleteAcademicSingleAttachement(
                        id = event.value.id,
                        level = event.value.level
                    )) {
                        is Resource.Failure -> {
                            _state.update {
                                it.copy(
                                    infoMsg = Message.Error(description = "Deletion Failed !!!", yesNoRequired = false),
                                    progress = null
                                )
                            }
                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            when (firestoreRepository.deleteAcademicSingleAttachement(
                                id = event.value.id, level = event.value.level
                            )) {
                                is Resource.Failure -> {

                                }

                                Resource.Loading -> {

                                }

                                is Resource.Success -> {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}