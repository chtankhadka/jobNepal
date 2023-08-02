package com.chetan.jobnepal.screens.academic

import android.net.Uri
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AcademicState(
    val uploadAttachementList: List<Uri> = listOf(),
    val downloadAttachementUrl: List<Pair<String,String>> = listOf(),
    val academicListResponse : UploadAcademicList = UploadAcademicList(),
    var showDialog: Boolean = false,
    var showEdit: Boolean = false,
    val selectedLevel: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null,

    ) : JobNepalScreenState(infoMsg, progress){
    companion object {
        const val SLC_SEE = "SEE"
        const val IAC = "IAC"
        const val BAC = "BAC"
        const val BSc_CSIT = "BSc_CSIT"

//        others
        const val CITIZENSHIP = "CITIZENSHIP"
    }
}
