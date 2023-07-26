package com.chetan.jobnepal.screens.dashboard


import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class DashboardState(
    val videoListResponse: UploadNewVideoLink = UploadNewVideoLink(),
    val applyList: List<UploadNewVideoLink> = listOf(),
    val profileUrl: String = "",
    val currentUserName: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
