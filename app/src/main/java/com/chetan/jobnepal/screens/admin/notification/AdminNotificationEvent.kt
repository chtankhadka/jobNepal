package com.chetan.jobnepal.screens.admin.notification

sealed interface AdminNotificationEvent{
    data class OnMessageChange(val value: String) : AdminNotificationEvent
    data class OnTitleChange(val value: String) : AdminNotificationEvent
    object OnSendNotification : AdminNotificationEvent
    object DissmissInfoMsg : AdminNotificationEvent

}