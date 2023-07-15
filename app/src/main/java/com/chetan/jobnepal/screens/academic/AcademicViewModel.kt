package com.chetan.jobnepal.screens.academic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
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
    private val repository: FirebaseStorageRepository
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
                        val resource = repository.uploadAcademicAttachement(
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
                            }
                        }

                    }
                }
            }
        }
    }
}