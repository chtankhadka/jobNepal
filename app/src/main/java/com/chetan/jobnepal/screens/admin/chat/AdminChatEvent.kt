package com.chetan.jobnepal.screens.admin.chat

import com.chetan.jobnepal.screens.user.chat.UserChatEvent

sealed interface AdminChatEvent{
    data object DismissInfoMsg: AdminChatEvent
    class GetChatRequestList(val id: String) : AdminChatEvent
    class GetChatHistory(val userName: String): AdminChatEvent
    data object SetChatHistory: AdminChatEvent
    class OnMsgChange(val value: String): AdminChatEvent

}