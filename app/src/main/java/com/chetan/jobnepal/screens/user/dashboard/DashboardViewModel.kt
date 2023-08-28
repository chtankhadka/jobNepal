package com.chetan.jobnepal.screens.user.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.searchhistory.SearchHistoryRequestResponse
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val preference: Preference,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseStorageRepository: FirebaseStorageRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init {
        _state.update {
            it.copy(
                profileUrl = preference.gmailProfile.toString(),
                currentUserName = preference.gmailUserName.toString(),
                nepaliLanguage = preference.isNepaliLanguage
            )
        }

        if (preference.isFirstTime) {
            setOneSignalId()
            firstTime()
            println("000000000000000000000" + preference.dbTable)
        }
        getAppliedFormData()
        getNewVideoLink()
        getSearchHistory()


    }

    private fun setOneSignalId() {
        println("i am here cheta")
        viewModelScope.launch {
            firestoreRepository.getOneSignalUserId()
        }
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            val resource1 = firestoreRepository.getSearchHistory()
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
                            searchListResponse = resource1.data
                        )
                    }
                }
            }
        }
    }

    fun resetApplyingList() {
        _state.update {
            it.copy(
                appliedIdsList = emptyList(),
                shikashakSewaAayog = FormAppliedList.DataColl.AcademicList(
                    jobList = emptyList(),
                    listName = "",
                    levels = emptyList()
                ),
                lokSewaAayog = FormAppliedList.DataColl.AcademicList(
                    jobList = emptyList(),
                    listName = "",
                    levels = emptyList()
                ),
                rastriyaAnusandhanBibhag = FormAppliedList.DataColl.AcademicList(
                    jobList = emptyList(),
                    listName = "",
                    levels = emptyList()
                )
            )
        }
    }

    fun getNewVideoLink() {
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
                            videoListResponse = resource1.data,
                            originalVideoListResponse = resource1.data
                        )
                    }
                }
            }
        }
    }

    fun getAppliedFormData() {
        viewModelScope.launch {
            _state.update {
                it.copy(progress = Progress(value = 0.0F, cancellable = false))
            }
            val resource2 = firestoreRepository.getAppliedFormData()
            when (resource2) {
                is Resource.Failure -> {
//                    _state.update {
//                        it.copy(
//                            infoMsg = Message.Error(description = "Please "),
//                            progress = null
//                        )
//                    }
                }

                Resource.Loading -> TODO()
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            infoMsg = null,
                            appliedListResponse = resource2.data,
                            appliedIdsList = resource2.data.map { dataColl -> dataColl.id }
                        )
                    }
                }
            }
        }
    }

    fun firstTime() {
        viewModelScope.launch {
            val reseting = firestoreRepository.createJobNepalCollection(
                listOf(
                    "academic",
                    "videoList",
                    "appliedList",
                    "Profile",
                    "searchHistory"
                )
            )
            when (reseting) {
                is Resource.Failure -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Error(
                                description = "Resetting",
                                isCancellable = false
                            )
                        )
                    }
                }

                Resource.Loading -> {}
                is Resource.Success -> {
                    preference.isFirstTime = false
                    _state.update {
                        it.copy(
                            infoMsg = null
                        )
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    val onEvent: (event: DashboardEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is DashboardEvent.ApplyNow -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                lottieImage = R.raw.pencil_walking,
                                isCancellable = false,
                                yesNoRequired = false,
                                description = "Great!!!"
                            )
                        )
                    }
                    val applylist =
                        state.value.videoListResponse.filter { it.id == state.value.selectedVideoId }
                    val applyNowRequest = firestoreRepository.uploadAppliedFormData(
                        FormAppliedList(
                            dataColl = applylist.map {
                                FormAppliedList.DataColl(
                                    id = it.id,
                                    title = it.title,
                                    videoLink = it.videoLink,
                                    description = it.description,
                                    apply = "applied",
                                    paymentSuccess = false,
                                    academicList = listOf(
                                        state.value.shikashakSewaAayog,
                                        state.value.lokSewaAayog,
                                        state.value.rastriyaAnusandhanBibhag
                                    )
                                )

                            }
                        )
                    )
                    when (applyNowRequest) {
                        is Resource.Failure -> {
                            _state.update {
                                it.copy(
                                    infoMsg = Message.Error(
                                        lottieImage = R.raw.loading,
                                        description = applyNowRequest.exception.message,
                                    )
                                )
                            }
                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    infoMsg = null,
                                )
                            }
                            resetApplyingList()
                            getAppliedFormData()
                            getNewVideoLink()
                        }
                    }
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

                        when (event.title) {
                            "शिक्षक सेवा आयोग" -> {
                                it.copy(shikashakSewaAayog = FormAppliedList.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }

                            "लोकसेवा आयोग" -> {
                                it.copy(lokSewaAayog = FormAppliedList.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }

                            "राष्ट्रिय अनुसन्धान विभाग" -> {
                                it.copy(rastriyaAnusandhanBibhag = FormAppliedList.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }


                            else -> {
                                it.copy(lokSewaAayog = FormAppliedList.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        FormAppliedList.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }
                        }
                    }
                }

                DashboardEvent.Logout -> {
                    preference.isFirstTime = true
                }

                is DashboardEvent.DeleteAppliedData -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Deleting..."
                            )
                        )
                    }
                    val deleteAppliedData = firestoreRepository.deleteAppliedFormData(event.value)
                    when (deleteAppliedData) {
                        is Resource.Failure -> {
                            _state.update {
                                it.copy(
                                    infoMsg = Message.Error(
                                        lottieImage = R.raw.delete_simple,
                                        yesNoRequired = false,
                                        isCancellable = false,
                                        description = "Deleting..."
                                    )
                                )

                            }
                            getAppliedFormData()
                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    infoMsg = null
                                )
                            }
                            getAppliedFormData()
                        }
                    }


                }

                DashboardEvent.DismissInfoMsg -> {
                    _state.update {
                        it.copy(infoMsg = null)
                    }
                }

                is DashboardEvent.JobsForDialog -> {
                    _state.update {
                        it.copy(jobsForDialog = event.value.academicList.map { academicList ->
                            Triple(
                                academicList.listName,
                                academicList.jobList.map { availableJobs ->
                                    availableJobs.jobName
                                },
                                academicList.levels.map { availableLevels ->
                                    availableLevels.levelName
                                }
                            )
                        })
                    }
                }

                is DashboardEvent.ChangeLanguage -> {
                    _state.update {
                        it.copy(
                            nepaliLanguage = event.value
                        )
                    }
                    preference.isNepaliLanguage = event.value
                }

                is DashboardEvent.OnQueryChangeOnSearch -> {
                    val search_list =
                        state.value.videoListResponse.filter { it.title.contains(event.value) }
                    _state.update {
                        it.copy(
                            searchText = event.value,
                            videoListResponse = search_list + state.value.videoListResponse.filter {
                                !it.title.contains(
                                    event.value
                                )
                            }
                        )
                    }

                }

                is DashboardEvent.OnQuerySearchOnSearch -> {
                    val resource = firestoreRepository.postSearchHistory(
                        SearchHistoryRequestResponse(
                            dataColl = listOf(
                                SearchHistoryRequestResponse.DataColl(
                                    searchValue = event.value,
                                    searchTime = LocalDateTime.now().toString()
                                )
                            )
                        )
                    )
                    when (resource) {
                        is Resource.Failure -> {
                        }

                        Resource.Loading -> {}
                        is Resource.Success -> {
                            getSearchHistory()
                        }
                    }
                }

                is DashboardEvent.OnQuerySearchDelete -> {
                    val list = state.value.searchListResponse.toMutableList()
                    val filterListIndex = list.indexOfFirst { it.searchTime == event.value }
                    if (filterListIndex != null && filterListIndex != -1) {
                        val resource = firestoreRepository.deleteSearchHistory(
                            SearchHistoryRequestResponse(
                                dataColl = list.filter { it.searchTime != event.value }

                            )
                        )
                        when (resource) {
                            is Resource.Failure -> {}
                            Resource.Loading -> {}
                            is Resource.Success -> {
                                getSearchHistory()
                            }
                        }
                    }

                }

                is DashboardEvent.OnFieldFilter -> {
                    val search_list = state.value.videoListResponse
                    _state.update {
                        it.copy(
                            videoListResponse = search_list.filter { it.title == event.value })
                    }
                }

                is DashboardEvent.OnProvinceFilter -> {
                    val search_list = state.value.videoListResponse
                    _state.update {
                        it.copy(
                            videoListResponse = search_list.filter { it.title == event.value })
                    }

                }

                DashboardEvent.OnRefresh -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                description = "Fetching Data",
                                isCancellable = false,
                                yesNoRequired = false
                            )
                        )
                    }
                    getNewVideoLink()
                    getAppliedFormData()
                    resetApplyingList()

                }

                is DashboardEvent.SelectVideo -> {
                    _state.update {
                        it.copy(
                            selectedVideoId = event.value
                        )
                    }
                }
            }
        }
    }
}