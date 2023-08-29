package com.chetan.jobnepal.screens.admin.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse
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
class AdminPaymentViewModel @Inject constructor(
    private val storageRepository: FirebaseStorageRepository,
    private val firebaseRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AdminPaymentState())
    val state: StateFlow<AdminPaymentState> = _state

    val onEvent: (event: AdminPaymentEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event){
                is AdminPaymentEvent.OnPaymentDetailsAdd -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Add new payment method"
                            )
                        )
                    }
                    val getUrlOfPic = storageRepository.uploadPaymentMethod(
                        listOf(
                            event.data.bankLogo,
                            event.data.bankQr
                        ),
                        bankName = event.data.bankName
                    )
                    when (getUrlOfPic){
                        is Resource.Failure -> {
                            _state.update {
                                it.copy(
                                    infoMsg = Message.Error(
                                        description = getUrlOfPic.exception.message,
                                        yesNoRequired = true
                                    ),
                                    progress = null
                                )
                            }
                        }
                        Resource.Loading -> TODO()
                        is Resource.Success -> {
                            _state.update {
                                it.copy(progress = null, downloadAttachementUrl = getUrlOfPic.data)
                            }
                            if (!getUrlOfPic.data.isEmpty()){
                                val addPaymentMethod = firebaseRepository.addAdminPaymentMethod(data =
                                getUrlOfPic.data.let {
                                    AddAdminPaymentMethodResponse(
                                        bankName = state.value.bankName,
                                        bankLogo = it.first().second,
                                        bankQr = it.first().second
                                    )
                                }
                                )
                                when(addPaymentMethod){
                                    is Resource.Failure ->{}
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
                        }
                    }



                }

                is AdminPaymentEvent.OnBankNameChange -> {
                    _state.update {
                        it.copy(
                            bankName = event.value
                        )
                    }

                }

                AdminPaymentEvent.DismissInfoMsg -> {
                    _state.update {
                        it.copy(
                            infoMsg = null
                        )
                    }
                }
            }
        }

    }
}