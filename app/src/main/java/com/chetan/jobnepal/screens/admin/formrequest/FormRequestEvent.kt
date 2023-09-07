package com.chetan.jobnepal.screens.admin.formrequest

import android.net.Uri

sealed interface FormRequestEvent{
    class GetFormRequestsOfId(val value: String) : FormRequestEvent
    class OnPaymentVerified(val user: String , val videoId: String) : FormRequestEvent
    class OnJobDetailsClicked(val user: String , val videoId: String)  : FormRequestEvent
    data object DismissInfoMsg: FormRequestEvent
    class UploadAdmitCard(val user: String, val videoId: String, val imgUri: Uri) : FormRequestEvent
    class UploadUnpaidReceipt(val user: String, val videoId: String, val imgUri: Uri) : FormRequestEvent
}