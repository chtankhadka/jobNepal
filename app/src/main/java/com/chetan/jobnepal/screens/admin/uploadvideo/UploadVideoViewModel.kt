package com.chetan.jobnepal.screens.admin.uploadvideo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
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
                                dataColl = listOf(
                                    UploadNewVideoLink.DataColl(
                                        id = state.id + GenerateRandomNumber.generateRandomNumber(
                                            111111..999999
                                        ),
                                        title = state.title,
                                        description = state.description,
                                        videoLink = state.url,
                                        publishedTime = state.publishedTime,
                                        academicList = listOf(
                                            state.shikashakSewaAayog,
                                            state.lokSewaAayog,
                                            state.rastriyaAnusandhanBibhag
                                        )

                                    )
                                )
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

                            Resource.Loading -> TODO()
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        infoMsg = null,
                                        progress = null,
                                        shikashakSewaAayog = UploadNewVideoLink.DataColl.AcademicList(
                                            jobList = emptyList(),
                                            listName = ""
                                        ),
                                        lokSewaAayog = UploadNewVideoLink.DataColl.AcademicList(
                                            jobList = emptyList(),
                                            listName = ""
                                        ),
                                        rastriyaAnusandhanBibhag = UploadNewVideoLink.DataColl.AcademicList(
                                            jobList = emptyList(),
                                            listName = ""
                                        )
                                    )
                                }
                            }
                        }
                    }

                }

                is UploadVideoEvent.DownloadVideoUrl -> {
                    _state.update {
                        it.copy(progress = Progress(value = 0.0F, cancellable = false))
                    }
                    when (val resource = repository.getNewVideoLink()) {
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
                                it.copy(
                                    videoList = UploadNewVideoLink(resource.data)
                                )
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

                UploadVideoEvent.Reset -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(description = "Reseting...")
                        )
                    }



                }

                is UploadVideoEvent.UpdateCheckedList -> {
                    _state.update {
                        when (event.title) {
                            "शिक्षक सेवा आयोग" -> {
                                it.copy(shikashakSewaAayog = UploadNewVideoLink.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }

                            "लोकसेवा आयोग" -> {
                                it.copy(lokSewaAayog = UploadNewVideoLink.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }

                            "राष्ट्रिय अनुसन्धान विभाग" -> {
                                it.copy(rastriyaAnusandhanBibhag = UploadNewVideoLink.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }


                            else -> {
                                it.copy(lokSewaAayog = UploadNewVideoLink.DataColl.AcademicList(
                                    listName = event.title,
                                    jobList = event.value.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableJobs(
                                            it
                                        )
                                    },
                                    levels = event.selectedLevels.map {
                                        UploadNewVideoLink.DataColl.AcademicList.AvailableLevels(
                                            it
                                        )
                                    }
                                ))
                            }
                        }

                    }
                }

                is UploadVideoEvent.SetCheckedList -> {
                    _state.update {
                        it.copy(showJobDialog = event.value)
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
