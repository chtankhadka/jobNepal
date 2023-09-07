package com.chetan.jobnepal.screens.admin.uploadvideo

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class UploadVideoState(
    var videoList: List<UploadNewVideoLink> = emptyList(),
    var showJobDialog: Boolean = false,
    var id: String = "",
    var videoId: String = "",
    var title: String = "",
    var videoUrl: String = "",
    var editProvince: String = "",
    var description: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
