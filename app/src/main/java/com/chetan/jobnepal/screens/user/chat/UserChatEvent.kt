package com.chetan.jobnepal.screens.user.chat

sealed interface UserChatEvent{
    class GetChatHistory(val value: String): UserChatEvent
    data object SetChatHistory: UserChatEvent
    class OnMsgChange(val value: String): UserChatEvent

}