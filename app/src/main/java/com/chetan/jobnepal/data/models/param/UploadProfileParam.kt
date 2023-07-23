package com.chetan.jobnepal.data.models.param

data class UploadProfileParam(
    val firstName: String? ="",
    val middleName: String? ="",
    val lastName: String? = "",
    val email: String? = "",
    val phoneNo: String? = "",
    val profileUrl: String? = "",
    val dob: String? = "",
    val gender: String? = "",
    val fatherFirstName: String? = "",
    val fatherMiddleName: String? = "",
    val fatherLastName: String? = ""
)
