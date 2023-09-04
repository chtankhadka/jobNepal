package com.chetan.jobnepal.screens.user.chat

import com.chetan.jobnepal.data.models.chat.UserChatModel


data class UserChatState(
    val userChatHistory : List<UserChatModel> = emptyList(),
    val userMsg: String = "",
    val videoId: String = "",
)