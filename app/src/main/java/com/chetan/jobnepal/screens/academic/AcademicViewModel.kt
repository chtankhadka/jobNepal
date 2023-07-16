package com.chetan.jobnepal.screens.academic

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AcademicViewModel @Inject constructor(
    private val storageRepository: FirebaseStorageRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AcademicState())
    val state: StateFlow<AcademicState> = _state

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
                                firestoreRepository.uploadAcademicData(
                                    data = UploadAcademicList(
                                        see = listOf(UploadAcademicList.seeColl( "1111","11","11"))
                                ))
                            }
                        }
                    }
                }

                is AcademicEvent.LevelSelected -> {
                    _state.update {
                        it.copy(selectedLevel = event.value)
                    }
                }
            }
        }
    }
}