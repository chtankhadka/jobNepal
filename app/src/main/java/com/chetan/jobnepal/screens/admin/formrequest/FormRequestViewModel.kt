package com.chetan.jobnepal.screens.admin.formrequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
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
class FormRequestViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val storageRepository: FirebaseStorageRepository
): ViewModel(){

    private val _state = MutableStateFlow(FormRequestState())
    val state: StateFlow<FormRequestState> = _state

    init {
        getUserPaymentVideoIdList()
    }
    fun getUserPaymentVideoIdList(){
        viewModelScope.launch {
            when(val receiptList = firestoreRepository.getUserPaymentVideoIdList()){
                is Resource.Failure -> {

                }
                Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            userPaymentVideoIdList = receiptList.data
                        )
                    }
                }
            }

        }
    }

    val onEvent : (event: FormRequestEvent) -> Unit = {event ->
        viewModelScope.launch {
            when (event) {
                is FormRequestEvent.OnPaymentVerified -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Payment Verified"
                            )
                        )
                    }

                    when (firestoreRepository.changeFormRequestToPaid(event.user, event.videoId)) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    infoMsg = null
                                )
                            }
                        }
                    }
                }

                FormRequestEvent.DismissInfoMsg -> {
                    _state.update {
                        it.copy(
                            infoMsg = null
                        )
                    }
                }

                is FormRequestEvent.GetFormRequestsOfId -> {
                    when (val receiptList =
                        firestoreRepository.getUserPaymentFormRequest(event.value)) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    paymentReceiptList = receiptList.data
                                )
                            }
                        }
                    }
                }

                is FormRequestEvent.OnJobDetailsClicked -> {
                    when(val getAppliedFormDetails = firestoreRepository.getAppliedFormDetails(event.user, event.videoId)){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    userAppliedFormDetails = getAppliedFormDetails.data
                                )
                            }
                        }
                    }
                }

                is FormRequestEvent.UploadAdmitCard -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Uploading..."
                            )
                        )
                    }
                    when(val getReceiptUrl = storageRepository.adminUploadReceipt(user = event.user, fileUri = event.imgUri)){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            when(val uploadReceipt = firestoreRepository.uploadAdmitCard(
                                user = event.user, videoId = event.videoId, url = getReceiptUrl.data.second, field = "admitCard"
                            )){
                                is Resource.Failure -> {

                                }
                                Resource.Loading -> {

                                }
                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            infoMsg =null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is FormRequestEvent.UploadUnpaidReceipt -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Uploading..."
                            )
                        )
                    }
                    when(val getReceiptUrl = storageRepository.adminUploadReceipt(user = event.user, fileUri = event.imgUri)){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            when(val uploadReceipt = firestoreRepository.uploadAdmitCard(
                                user = event.user, videoId = event.videoId, url = getReceiptUrl.data.second, field = "unPaidBankReceipt"
                            )){
                                is Resource.Failure -> {

                                }
                                Resource.Loading -> {

                                }
                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            infoMsg =null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}