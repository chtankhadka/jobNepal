package com.chetan.jobnepal.screens.admin.dashboard


import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AdminDashboardState(
    val searchText: String = "",
    val profileUrl: String = "",
    val showApplyDialog: Boolean = false,
    val currentUserName: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
