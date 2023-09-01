package com.chetan.jobnepal.screens.admin.formrequest

sealed interface FormRequestEvent{
    class GetFormRequestsOfId(val value: String) : FormRequestEvent
    class OnPaymentVerified(val user: String , val videoId: String) : FormRequestEvent
    class OnJobDetailsClicked(val user: String , val videoId: String)  : FormRequestEvent
    object DismissInfoMsg: FormRequestEvent
}