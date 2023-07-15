package com.chetan.jobnepal.screens.academic

import android.net.Uri
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AcademicState(
    val uploadAttachementList: List<Uri> = listOf(),
    val downloadAttachementUrl: List<String> = listOf(),
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
