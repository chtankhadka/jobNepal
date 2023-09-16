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
    data class OnPhoneChange(val value: String) : ProfileEvent
    data class OnGenderChange(val value: Gender?) : ProfileEvent

    // Permanent address
    data class PermanentProvince(val value: String): ProfileEvent
    data class PermanentDistrict(val value: String): ProfileEvent
    data class PermanentMunicipality(val value: String): ProfileEvent
    data class PermanentVillage(val value: String): ProfileEvent
    data class PermanentWard(val value: String): ProfileEvent

    data class TemporaryProvince(val value: String): ProfileEvent
    data class TemporaryDistrict(val value: String): ProfileEvent
    data class TemporaryMunicipality(val value: String): ProfileEvent
    data class TemporaryVillage(val value: String): ProfileEvent
    data class TemporaryWard(val value: String): ProfileEvent

    //fathers details

    data class OnFatherFirstNameChange(val value: String) : ProfileEvent
    data class OnFatherMiddleNameChange(val value: String) : ProfileEvent
    data class OnFatherLastNameChange(val value: String) : ProfileEvent

    //fathers details

    data class OnMotherFirstNameChange(val value: String) : ProfileEvent
    data class OnMotherMiddleNameChange(val value: String) : ProfileEvent
    data class OnMotherLastNameChange(val value: String) : ProfileEvent

    //Grand father
    data class GrandFathersFirstName(val value: String) : ProfileEvent
    data class GrandFathersMiddleName(val value: String) : ProfileEvent
    data class GrandFathersLastName(val value: String): ProfileEvent

    // Husband Wife
    data class HusbandWifeFirstName(val value: String) : ProfileEvent
    data class HusbandWifeMiddleName(val value: String) : ProfileEvent
    data class HusbandWifeLastName(val value: String): ProfileEvent

    data class Upload(val value: Uri) : ProfileEvent
    object DismissInfoMsg: ProfileEvent
}