package com.chetan.jobnepal.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
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
class DashboardViewModel @Inject constructor(
    private val preference: Preference,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init {
        _state.update {
            it.copy(
                profileUrl = preference.gmailProfile.toString(),
                currentUserName = preference.gmailUserName.toString()
            )
        }
        getNewVideoLink()
        getAppliedFormData()


    }
    fun getNewVideoLink(){
        viewModelScope.launch {
            val resource1 = firestoreRepository.getNewVideoLink()
            when (resource1) {
                is Resource.Failure -> {
                    _state.update {
                        it.copy(
                            infoMsg = null,
                            progress = null
                        )
                    }
                }

                Resource.Loading -> TODO()
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            videoListResponse = resource1.data
                        )
                    }
                }
            }
        }
    }
    fun getAppliedFormData(){
        viewModelScope.launch {
            _state.update {
                it.copy(progress = Progress(value = 0.0F, cancellable = false))
            }
            val resource2 = firestoreRepository.getAppliedFormData()
            when (resource2) {
                is Resource.Failure -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Error(description = resource2.exception.message),
                            progress = null
                        )
                    }
                }

                Resource.Loading -> TODO()
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            infoMsg = null,
                            appliedListResponse = resource2.data,
                            appliedIdsList = resource2.data.dataColl.map { dataColl -> dataColl.id }
                        )
                    }
                }
            }
        }
    }


    val onEvent: (event: DashboardEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is DashboardEvent.ApplyNow -> {
                    _state.update {
                        it.copy(
                            infoMsg = null
                        )
                    }
                    val list =
                        state.value.videoListResponse.dataColl.filter { it.id == event.value.id }
                   val applyNowRequest = firestoreRepository.uploadAppliedFormData(
                        FormAppliedList(
                            dataColl = list.map {
                                FormAppliedList.DataColl(
                                    id = event.value.id,
                                    title = event.value.title,
                                    videoLink = event.value.videoLink,
                                    description = event.value.description,
                                    apply = "applied",
                                    paymentSuccess = false,
                                    academicList = listOf(
                                        state.value.technicalList,
                                        state.value.nonTechnicalList
                                    )
                                )

                            }
                        )
                    )
                    when (applyNowRequest){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {}
                        is Resource.Success -> {

                        }
                    }
                }

                is DashboardEvent.ApplyLetter -> {
                    val list =
                        state.value.videoListResponse.dataColl.filter { it.id == event.value.id }
                    firestoreRepository.uploadAppliedFormData(
                        FormAppliedList(
                            dataColl = list.map {
                                FormAppliedList.DataColl(
                                    id = event.value.id,
                                    title = event.value.title,
                                    videoLink = event.value.videoLink,
                                    description = event.value.description,
                                    apply = "applyLater",
                                    paymentSuccess = false,
                                    academicList = listOf(
                                        state.value.technicalList,
                                        state.value.nonTechnicalList
                                    )
                                )

                            }
                        )
                    )
                }

                is DashboardEvent.ShowApplyDialog -> {
                    _state.update {
                        it.copy(
                            showApplyDialog = event.value
                        )
                    }
                }

                is DashboardEvent.UpdateCheckedList -> {
                    _state.update {
                        if (event.title == "technicalList") {
                            it.copy(technicalList = FormAppliedList.DataColl.AcademicList(
                                listName = event.title,
                                jobList = event.value.map {
                                    FormAppliedList.DataColl.AcademicList.AvailableJobs(it)
                                },
                                levels = event.selectedLevels.map {
                                    FormAppliedList.DataColl.AcademicList.AvailableLevels(it)
                                }
                            ))
                        } else {
                            it.copy(nonTechnicalList = FormAppliedList.DataColl.AcademicList(
                                listName = event.title,
                                jobList = event.value.map {
                                    FormAppliedList.DataColl.AcademicList.AvailableJobs(it)
                                },
                                levels = event.selectedLevels.map {
                                    FormAppliedList.DataColl.AcademicList.AvailableLevels(it)
                                }
                            ))
                        }
                    }
                }

                is DashboardEvent.DeleteAppliedData -> {
                    _state.update {
                        it.copy(infoMsg = Message.Loading(
                            lottieImage = R.raw.delete_simple,
                            description = "Deleting...",
                            yesNoRequired = false
                            ))
                    }
                    firestoreRepository.deleteAppliedFormData(event.value)
                    getAppliedFormData()

                }

                DashboardEvent.DismissInfoMsg -> {
                    _state.update {
                        it.copy(infoMsg = null)
                    }
                }
            }
        }
    }
}