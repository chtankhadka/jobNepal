package com.chetan.jobnepal.screens.admin.chat

import com.chetan.jobnepal.data.models.chat.ChatNotificationModel
import com.chetan.jobnepal.data.models.chat.UserChatModel
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AdminChatState(
    val videoIdList: List<String> = emptyList(),
    val chatUsersList: List<ChatNotificationModel> = emptyList(),
    val userChatHistory : List<UserChatModel> = emptyList(),
    val user: String = "",
    val userMsg: String = "",
    val vId: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
