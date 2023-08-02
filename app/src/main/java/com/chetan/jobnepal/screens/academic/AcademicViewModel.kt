package com.chetan.jobnepal.screens.academic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.House
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
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
import javax.inject.Inject

@HiltViewModel
class AcademicViewModel @Inject constructor(
    private val storageRepository: FirebaseStorageRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AcademicState())
    val state: StateFlow<AcademicState> = _state

    init {
        getAcademicResponse()
    }

    val onEvent: (event: AcademicEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is AcademicEvent.UploadAttachement -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(description = "Uploading your data...", isCancellable = false, yesNoRequired = false))
                    }
                    val state = state.value
                    state.uploadAttachementList.let {
                        _state.update {
                            it.copy(progress = Progress(value = 0.0F))
                        }
                        val resource = storageRepository.uploadAcademicAttachement(
                            event.value, state.selectedLevel
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
                                _state.update {
                                    it.copy(progress = null, downloadAttachementUrl = resource.data)
                                }


                                //academic data
                                if (!resource.data.isEmpty()) {
                                    val academicUploadResponse = when (state.selectedLevel) {
                                        AcademicState.SLC_SEE -> {
                                            firestoreRepository.uploadAcademicData(
                                                data = UploadAcademicList(SEE = resource.data.map {
                                                    UploadAcademicList.seeColl(
                                                        "SLC",
                                                        "11",
                                                        it.first, it.second
                                                    )
                                                }),
                                                selectedLevel = state.selectedLevel
                                            )
                                        }

                                        AcademicState.IAC -> {
                                            firestoreRepository.uploadAcademicData(
                                                data = UploadAcademicList(IAC = resource.data.map {
                                                    UploadAcademicList.iacColl(
                                                        "IAC",
                                                        "12",
                                                        it.first,
                                                        it.second
                                                    )
                                                }),
                                                selectedLevel = "IAC"
                                            )
                                        }

                                        AcademicState.BSc_CSIT -> {
                                            firestoreRepository.uploadAcademicData(
                                                data = UploadAcademicList(BSc_CSIT = resource.data.map {
                                                    UploadAcademicList.bsccsitColl(
                                                        "BSc.CSIT",
                                                        "12",
                                                        it.first, it.second
                                                    )
                                                }),
                                                selectedLevel = "BSc_CSIT"
                                            )
                                        }
                                        AcademicState.CITIZENSHIP -> {
                                            firestoreRepository.uploadAcademicData(
                                                data = UploadAcademicList(CITIZENSHIP = resource.data.map {
                                                    UploadAcademicList.citizenship(
                                                        "CITIZENSHIP",
                                                        "12",
                                                        it.first, it.second
                                                    )
                                                }),
                                                selectedLevel = "CITIZENSHIP"
                                            )
                                        }

                                        else -> {
                                            firestoreRepository.uploadAcademicData(
                                                data = UploadAcademicList(IAC = resource.data.map {
                                                    UploadAcademicList.iacColl(
                                                        "IAC",
                                                        "12",
                                                        it.first,
                                                        it.second
                                                    )
                                                }),
                                                selectedLevel = "IAC"
                                            )
                                        }
                                    }
                                    when (academicUploadResponse) {
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
                                                    downloadAttachementUrl = resource.data,
                                                    showDialog = false
                                                )
                                            }
                                            getAcademicResponse()
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

                is AcademicEvent.Delete -> {
                    _state.update {
                        it.copy(
                            progress = Progress(value = 0F)
                        )
                    }
//                    val resource = storageRepository.deleteAcademicAtachements(event.value,event.name)
//                    when (resource){
//                        is Resource.Failure -> {
//                            _state.update {
//                                it.copy(
//                                    infoMsg = Message.Error(description = resource.exception.message),
//                                    progress = null
//                                )
//                            }
//                        }
//
//                        is Resource.Loading -> TODO()
//                        is Resource.Success -> {
//                            getAcademicResponse()
//                        }
//                    }

                    val resource1 = firestoreRepository.deleteAcademicData("", listOf(""))
                    when (resource1) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {

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
            }
        }
    }

    private fun getAcademicResponse() {
        _state.update {
            it.copy(
                infoMsg = Message.Loading(
                    lottieImage = R.raw.loading,
                    description = "Please Wait",
                    isCancellable = false,
                    yesNoRequired = false
                )
            )
        }
        viewModelScope.launch {
            val state = state.value
            state.academicListResponse.let {
                _state.update {
                    it.copy(progress = Progress(value = 0.0F))
                }
                val resource = firestoreRepository.getAcademicData()
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
    }
}