package com.chetan.jobnepal.data.models.formrequest

data class FormRequestJobDetails(
    val id: String = "",
    val title: String = "",
    val videoLink: String = "",
    val description: String = "",
    val apply: String = "",
    val paymentSuccess: Boolean = false,
    val jobInfo: String = ""
)