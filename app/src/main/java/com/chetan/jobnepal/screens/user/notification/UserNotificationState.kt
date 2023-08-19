package com.chetan.jobnepal.screens.user.notification

import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class UserNotificationState(
    val userNotificationResponse : List<StoreNotificationRequestResponse> = emptyList(),
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
