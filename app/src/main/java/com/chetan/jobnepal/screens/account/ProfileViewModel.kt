package com.chetan.jobnepal.screens.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    init {

    }

    val onEvent: (event: ProfileEvent) -> Unit = {event ->
        when (event){

            is ProfileEvent.OnFirstNameChange -> {
                _state.update {
                    it.copy(editFirstName = event.value)
                }
            }
            is ProfileEvent.OnMiddleNameChange -> {
                _state.update {
                    it.copy(editMiddleName = event.value )
                }
            }

            is ProfileEvent.OnEmailChange -> {
                _state.update {
                    it.copy(editEmail = event.value)
                }
            }

            is ProfileEvent.OnLastNameChange -> {
                _state.update {
                    it.copy(editLastName = event.value)
                }
            }
            is ProfileEvent.OnGenderChange -> {
                _state.update {
                    it.copy(editGender = event.value)
                }
            }

            is ProfileEvent.OnFatherFirstNameChange ->{
                _state.update {
                    it.copy(editFatherFirstName = event.value)
                }
            }
            is ProfileEvent.OnFatherMiddleNameChange -> {

                _state.update {
                    it.copy(editFatherMiddleName = event.value)
                }
            }
            is ProfileEvent.OnFatherLastNameChange -> {
                _state.update {
                    it.copy(editFatherLastNam = event.value)
                }
            }

            ProfileEvent.Upload -> {

            }
        }

    }
}