package com.chetan.jobnepal.data.models.dashboard

data class UploadAppliedFormDataRequest(
    val id: String = "",
    val title: String = "",
    val shortVideoId: String = "",
    val videoUrl: String = "",
    val appliedTime: String = "",
    val description: String = "",
    val apply: String = "",
    val paymentSuccess: Boolean = false,
    val jobInfo: String = "",
    val unPaidBankReceipt: String = "",
    val paidBankReceipt: String = "",
    val admitCard: String = ""

)

