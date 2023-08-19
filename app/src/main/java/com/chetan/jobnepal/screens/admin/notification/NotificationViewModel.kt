package com.chetan.jobnepal.screens.admin.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.oneSignal.PushNotificationRequest
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.data.repository.onesignalrepository.OneSignalRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val firebaseRepository: FirestoreRepository,
    private val oneSiganlRepository: OneSignalRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state


    val onEvent: (event: NotificationEvent) -> Unit = { event ->
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is NotificationEvent.OnMessageChange -> {
                    _state.update {
                        it.copy(
                            message = event.value
                        )
                    }
                }

                NotificationEvent.OnSendNotification -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Sending"
                            )
                        )
                    }

                    try {

                        val sendNotification = oneSiganlRepository.pushNotification(
                            PushNotificationRequest(
                                contents = mapOf("en" to state.value.message),
                                name = "Offer"
                            )
                        )
                        when (sendNotification) {
                            is Resource.Failure -> {
                                _state.update {
                                    it.copy(
                                        infoMsg = Message.Error(
                                            lottieImage = R.raw.delete_simple,
                                            yesNoRequired = false,
                                            isCancellable = false,
                                            description = "Error..."
                                        )
                                    )

                                }
                            }

                            Resource.Loading -> {

                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        infoMsg = Message.Success(
                                            lottieImage = R.raw.pencil_walking,
                                            isCancellable = true,
                                            description = "Success"
                                        )
                                    )
                                }
                            }
                        }
                    } catch (e: HttpException) {
                        _state.update {
                            it.copy(
                                infoMsg = null
                            )
                        }
                        e.printStackTrace()
                    } catch (e: Throwable) {
                        _state.update {
                            it.copy(
                                infoMsg = null
                            )
                        }
                        e.printStackTrace()
                    }
                }

                NotificationEvent.DissmissInfoMsg -> {
                    _state.update {
                        it.copy(infoMsg = null)
                    }
                }
            }
        }
    }


}