package com.chetan.jobnepal.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.local.Preference
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
        private val preference: Preference,
): ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> =  _state

    init {
        _state.update {
            it.copy(
                profileUrl = preference.gmailProfile.toString(),
                currentUserName = preference.gmailUserName.toString()

            )
        }
    }


    val onEvent: (event: DashboardEvent) -> Unit = { event ->
        viewModelScope.launch {
            when(event){
                is DashboardEvent.Apply -> {

                }
            }
        }
    }
}