package com.chetan.jobnepal.screens.user.account

import com.chetan.jobnepal.data.enums.Gender
import com.chetan.jobnepal.data.models.Address
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class ProfileState(
    val profileResponse : UploadProfileParam = UploadProfileParam(),
    val editFirstName: String = "",
    val editMiddleName: String = "",
    val editLastName: String = "",
    val editDob: String = "",
    val imageUrl: String = "",
    val editGender: Gender? = null,
    val editEmail: String = "",

    //address

    val address: List<Address.Province> = emptyList(),
    val provience: String = "",
    val district: String = "",
    val municipality: String = "",
    val wardNo: String = "",
    val villageName: String = "",


    val editFatherFirstName: String = "",
    val editFatherMiddleName: String = "",
    val editFatherLastNam: String = "",
    var isOtherVisible: Boolean = false,
    override val infoMsg: Message? = null,
    override val progress: Progress? = null

) : JobNepalScreenState(infoMsg, progress) {

    fun areAllDataFilled(): Boolean {
        return editFirstName.isNotBlank()
                && editMiddleName.isNotBlank()
                && editLastName.isNotBlank()
                && !editGender?.value.isNullOrBlank()
                && editEmail.isNotBlank()
                && editDob.isNotBlank()
                && editFatherFirstName.isNotBlank()
                && editFatherMiddleName.isNotBlank()
                && editFatherLastNam.isNotBlank()
    }
//    fun asUpdateProfileParam() = UpdateProfilePaream()
}