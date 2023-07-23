package com.chetan.jobnepal.screens.sign_in.newlogin

import androidx.lifecycle.ViewModel
import com.chetan.jobnepal.data.local.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(val preference: Preference) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
        if (state.value.isSignInSuccessful){
            preference.dbTable = result.data?.userEmail?.split("@")?.get(0)
        }

    }

    fun resetState() {
        _state.update { SignInState() }
    }
}