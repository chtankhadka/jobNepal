package com.chetan.jobnepal.data.models.adminpayment

data class PaidPaymentDetails(
    val emailAddress: String ="",
    val videoId: String = "",
    val receiptLink: String = "",
    val approved: Boolean = false
)
