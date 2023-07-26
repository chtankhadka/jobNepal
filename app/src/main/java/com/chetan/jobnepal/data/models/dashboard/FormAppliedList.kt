package com.chetan.jobnepal.data.models.dashboard

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

data class FormAppliedList(
    val dataColl: List<DataColl> = emptyList()
){
    data class DataColl(
        val id: String = "",
        val title: String = "",
        val videoLink: String = "",
        val description: String = "",
        val apply: String = "",
        val paymentSuccess: Boolean = false
    )
}


