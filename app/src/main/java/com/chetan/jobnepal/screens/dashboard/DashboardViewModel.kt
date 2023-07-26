package com.chetan.jobnepal.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
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

        viewModelScope.launch {
            val resource1 = firestoreRepository.getNewVideoLink()
            when (resource1) {
                is Resource.Failure -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Error(description = resource1.exception.message),
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


    val onEvent: (event: DashboardEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                is DashboardEvent.Apply -> {
                    firestoreRepository.uploadAppliedFormData(
                            FormAppliedList(
                                id = event.value.id,
                                title = event.value.title,
                                videoLink = event.value.videoLink,
                                description = event.value.description,
                                apply = "applied",
                                paymentSuccess = false
                            )
                        )
                }
            }
        }
    }
}