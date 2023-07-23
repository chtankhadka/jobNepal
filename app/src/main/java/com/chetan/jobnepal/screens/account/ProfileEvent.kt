package com.chetan.jobnepal.screens.account

import com.chetan.jobnepal.data.enums.Gender

sealed interface ProfileEvent {
    data class OnFirstNameChange(val value: String) : ProfileEvent
    data class OnMiddleNameChange(val value: String) : ProfileEvent
    data class OnLastNameChange(val value: String) : ProfileEvent
    data class OnEmailChange(val value: String) : ProfileEvent
    data class OnGenderChange(val value: Gender?) : ProfileEvent

    data class OnFatherFirstNameChange(val value: String) : ProfileEvent
    data class OnFatherMiddleNameChange(val value: String) : ProfileEvent
    data class OnFatherLastNameChange(val value: String) : ProfileEvent

    data class OnOtherDetailsClicked(val value: Boolean) : ProfileEvent
    object Upload : ProfileEvent
}