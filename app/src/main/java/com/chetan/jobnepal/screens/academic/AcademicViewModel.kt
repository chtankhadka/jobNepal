package com.chetan.jobnepal.screens.academic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.param.UploadAcademicList
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
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
                    val state = state.value
                    state.uploadAttachementList.let {
                        _state.update {
                            it.copy(progress = Progress(value = 0.0F))
                        }
                        val resource = storageRepository.uploadAcademicAttachement(
                            event.value
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

                            is Resource.Loading -> TODO()
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(progress = null, downloadAttachementUrl = resource.data)
                                }
                                //academic upload
                                val academicUploadResponse = when (state.selectedLevel) {
                                    AcademicState.SLC_SEE -> {
                                        firestoreRepository.uploadAcademicData(
                                            data = UploadAcademicList(SEE = state.downloadAttachementUrl.map {
                                                UploadAcademicList.seeColl("SLC", "11", it)
                                            }),
                                            selectedLevel = state.selectedLevel
                                        )
                                    }

                                    AcademicState.IAC -> {
                                        firestoreRepository.uploadAcademicData(
                                            data = UploadAcademicList(IAC = state.downloadAttachementUrl.map {
                                                UploadAcademicList.iacColl("IAC", "12", it)
                                            }),
                                            selectedLevel = "IAC"
                                        )
                                    }
                                    AcademicState.BSC_CSIT -> {
                                        firestoreRepository.uploadAcademicData(
                                            data = UploadAcademicList(BSC_CSIT = state.downloadAttachementUrl.map {
                                                UploadAcademicList.bsccsitColl("BSc.CSIT", "12", it)
                                            }),
                                            selectedLevel = "BSc_CSIT"
                                        )
                                    }

                                    else -> {
                                        firestoreRepository.uploadAcademicData(
                                            data = UploadAcademicList(IAC = state.downloadAttachementUrl.map {
                                                UploadAcademicList.iacColl("IAC", "12", "else")
                                            }),
                                            selectedLevel = "IAC"
                                        )
                                    }
                                }
                                when (academicUploadResponse) {
                                    is Resource.Failure -> {
                                        _state.update {
                                            it.copy(
                                                infoMsg = Message.Error(description = "Data Not uploaded"),
                                                progress = null
                                            )
                                        }
                                    }

                                    Resource.Loading -> TODO()
                                    is Resource.Success -> {
                                        _state.update {
                                            it.copy(
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
            }
        }
    }

    private fun getAcademicResponse() {
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

                    Resource.Loading -> TODO()
                    is Resource.Success -> {
                        _state.update {
                            it.copy(progress = null, academicListResponse = resource.data)

                        }
                    }
                }
            }
        }
    }
}