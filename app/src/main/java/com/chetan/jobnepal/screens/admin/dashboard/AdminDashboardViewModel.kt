package com.chetan.jobnepal.screens.admin.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.oneSignal.PushNotificationRequest
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.data.repository.onesignalrepository.OneSignalRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val preference: Preference,
    private val firestoreRepository: FirestoreRepository,
    private val oneSiganlRepository: OneSignalRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AdminDashboardState())
    val state: StateFlow<AdminDashboardState> = _state

    init {
        _state.update {
            it.copy(
                profileUrl = preference.gmailProfile.toString(),
                currentUserName = preference.gmailUserName.toString(),
//                nepaliLanguage = preference.isNepaliLanguage
            )
        }
    }

    val onEvent: (event: AdminDashboardEvent) -> Unit = { event ->
        viewModelScope.launch (Dispatchers.IO){
            when (event) {
                AdminDashboardEvent.DismissInfoMsg -> {

                }
            }
        }
    }
}