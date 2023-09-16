package com.chetan.jobnepal.data.models.profile

data class UploadProfileParam(
    val firstName: String? ="",
    val middleName: String? ="",
    val lastName: String? = "",
    val email: String? = "",
    val phoneNo: String? = "",
    val profileUrl: String? = "",
    val photoName: String? = "",
    val dob: String? = "",
    val gender: String? = "",


    val fatherFirstName: String? = "",
    val fatherMiddleName: String? = "",
    val fatherLastName: String? = "",

    val motherFirstName: String? = "",
    val motherMiddleName: String? = "",
    val motherLastName: String? = "",



    val grandfatherFirstName: String? = "",
    val grandfatherMiddleName: String? = "",
    val grandfatherLastName: String? = "",

    val husbandWifeFirstName: String? = "",
    val husbandWifeMiddleName: String? = "",
    val husbandWifeLastName: String? = "",

    val province: String? = "",
    val district: String? = "",
    val municipality: String? = "",
    val wardNo: String? = "",
    val villageName: String? = "",

    //Temporary address
    val tprovience: String? = "",
    val tdistrict: String? = "",
    val tmunicipality: String? = "",
    val twardNo: String? = "",
    val tvillageName: String? = "",

    )
