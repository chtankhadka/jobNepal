package com.chetan.jobnepal.screens.admin.adminbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.param.UserDashboardUpdateNoticeRequestResponse
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AdminBottomSheetViewModel @Inject constructor(
    private val firebaseRepository: FirestoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AdminBottomSheetState())
    val state: StateFlow<AdminBottomSheetState> = _state


    val onEvent: (event: AdminBottomSheetEvent) -> Unit = { event ->
        viewModelScope.launch {
            when (event) {
                AdminBottomSheetEvent.UpdateBottomSheetNotice -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Loading(
                                yesNoRequired = false,
                                isCancellable = false,
                                description = "Updating..."
                            )
                        )
                    }

                    val cost = state.value.totalCost
                    val discountPer = state.value.discountPercentage
                    val finalAmt = (discountPer.toFloat() / 100f) * cost.toFloat()
                    val updateNoticeRequest = firebaseRepository.updateNoticeUserDashboard(
                        data = UserDashboardUpdateNoticeRequestResponse(
                            totalCost = cost,
                            discountPercentage = discountPer,
                            discountAmt = (cost.toFloat() - finalAmt).roundToInt().toString()
                        )
                    )
                    when (updateNoticeRequest){
                        is Resource.Failure -> {

                        }
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    infoMsg = null
                                )
                            }
                        }
                    }
                }

                AdminBottomSheetEvent.DissmissInfoMsg -> {
                    _state.update {
                        it.copy(infoMsg = null)
                    }
                }

                is AdminBottomSheetEvent.OnDiscountPercentageChange -> {
                    _state.update {
                        it.copy(
                            discountPercentage = event.value
                        )
                    }
                }

                is AdminBottomSheetEvent.OnTotalCostChange -> {
                    _state.update {
                        it.copy(
                            totalCost = event.value
                        )
                    }
                }
            }
        }
    }
}