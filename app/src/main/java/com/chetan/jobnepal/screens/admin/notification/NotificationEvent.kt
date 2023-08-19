package com.chetan.jobnepal.screens.admin.notification

import com.chetan.jobnepal.screens.user.dashboard.DashboardEvent

sealed interface NotificationEvent{
    data class OnMessageChange(val value: String) : NotificationEvent
    object OnSendNotification : NotificationEvent
    object DissmissInfoMsg : NotificationEvent

}