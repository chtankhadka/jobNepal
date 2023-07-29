package com.chetan.jobnepal.screens.admin.uploadvideo

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import java.time.LocalDateTime

data class UploadVideoState(
        var videoList: UploadNewVideoLink= UploadNewVideoLink(),
        val technicalList: List<UploadNewVideoLink.DataColl.AcademicList.TechnicalList> = emptyList(),
        val nonTechnicalList: List<UploadNewVideoLink.DataColl.AcademicList.NonTechnicalList> = emptyList(),
        var showJobDialog: Boolean = false,
        var id: String = "",
        var url: String = "",
        var title: String ="",
        var description: String = "",
        var publishedTime: String = "",
        override val infoMsg: Message? = null,
        override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
