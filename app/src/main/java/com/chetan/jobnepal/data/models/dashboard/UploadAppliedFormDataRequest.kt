package com.chetan.jobnepal.data.models.dashboard

data class UploadAppliedFormDataRequest(
    val id: String = "",
    val title: String = "",
    val videoLink: String = "",
    val description: String = "",
    val apply: String = "",
    val paymentSuccess: Boolean = false,
    val jobInfo: String = ""
)

