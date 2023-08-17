package com.chetan.jobnepal.screens.user.account

import android.net.Uri
import com.chetan.jobnepal.data.enums.Gender
import com.chetan.jobnepal.data.models.Address

sealed interface ProfileEvent {
    data class OnFirstNameChange(val value: String) : ProfileEvent
    data class OnMiddleNameChange(val value: String) : ProfileEvent
    data class OnLastNameChange(val value: String) : ProfileEvent
    data class OnDobChange(val value: String) : ProfileEvent
    data class OnEmailChange(val value: String) : ProfileEvent
    data class OnGenderChange(val value: Gender?) : ProfileEvent

    // address
    data class PermanentProvince(val value: String): ProfileEvent
    data class PermanentDistrict(val value: String): ProfileEvent
    data class PermanentMunicipality(val value: String): ProfileEvent




    data class OnFatherFirstNameChange(val value: String) : ProfileEvent
    data class OnFatherMiddleNameChange(val value: String) : ProfileEvent
    data class OnFatherLastNameChange(val value: String) : ProfileEvent

    data class OnOtherDetailsClicked(val value: Boolean) : ProfileEvent
    data class Upload(val value: Uri) : ProfileEvent
}