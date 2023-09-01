package com.chetan.jobnepal.screens.user.academic

import android.net.Uri
import com.chetan.jobnepal.data.models.academic.UploadAcademicData
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AcademicState(
    val uploadAttachementList: List<Uri> = listOf(),
    val downloadAttachementUrl: List<Pair<String,String>> = listOf(),
    val academicListResponse : List<UploadAcademicData> = emptyList(),
    var showDialog: Boolean = false,
    var showEdit: Boolean = false,
    val selectedLevel: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null,

    ) : JobNepalScreenState(infoMsg, progress){
}
