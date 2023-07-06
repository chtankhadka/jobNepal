package com.chetan.jobnepal.screens.admin.uploadvideo

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UploadVideoViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UploadVideoState())
    val state: StateFlow<UploadVideoState> = _state

    val onEvent: (event: UploadVideoEvent) -> Unit = {event ->
        viewModelScope.launch {
           when (event){
               is UploadVideoEvent.UploadVideoUrl -> {
                   val state = state.value
                   state.videoList.let {
                       _state.update {
                           it.copy(progress = Progress(value = 0.0F))
                       }
                       val resource = repository.uploadNewVideoLink(UploadNewVideoLink(
                           id = state.id,
                           title = state.title,
                           description = state.description,
                           videoLink = state.url,
                       ))
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
                                   it.copy(progress = null)
                               }
                           }
                       }
                   }

               }
               is UploadVideoEvent.DownloadVideoUrl -> {
                   _state.update {
                       it.copy(progress = Progress(value = 0.0F, cancellable = false))
                   }
                   when (val resource = repository.getNewVideoLink()){
                       is Resource.Failure -> {
                           _state.update {
                               it.copy(
                                   infoMsg = Message.Error(description = resource.exception.message),
                                   progress = null
                               )
                           }
                       }
                       Resource.Loading -> TODO()
                       is Resource.Success ->{
                            _state.update {
                                it.copy(
                                    videoList = resource.data
                                )
                            }
                           Timber.d(resource.data.toString())


                       }
                   }

               }

               is UploadVideoEvent.IdChange -> {
                   _state.update {
                       it.copy(id = event.value)
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
           }
        }

    }
}