package com.chetan.jobnepal.data.models.chat

data class UserChatModel(
    val self : Boolean = false,
    val msg: String = "",
    val msgId: String = "",
    val videoId : String = "",
    val userName: String = ""
)
