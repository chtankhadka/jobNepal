package com.chetan.jobnepal.data.models.chat

data class ChatNotificationModel(
    val newFromAdmin: Boolean = false,
    val newFromUser: Boolean = false,
    val user: String = ""
)
