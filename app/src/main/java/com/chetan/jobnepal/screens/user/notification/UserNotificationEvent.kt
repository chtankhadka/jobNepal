package com.chetan.jobnepal.screens.user.notification

sealed interface UserNotificationEvent{
    data class readState(val value: String): UserNotificationEvent

}