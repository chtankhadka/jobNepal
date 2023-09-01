package com.chetan.jobnepal.screens.user.academic

import android.net.Uri
import com.chetan.jobnepal.data.models.academic.UploadAcademicData

sealed interface AcademicEvent{
    class UploadAttachement(var value: List<Uri>) : AcademicEvent
    class GetAcademicEvent(var value: String) : AcademicEvent
    class LevelSelected(var value: String) : AcademicEvent
    class ShowDialog(var value: Boolean) : AcademicEvent
    class ShowEdit(var value: Boolean) : AcademicEvent
    class DeleteSelectedItem(val value: UploadAcademicData) : AcademicEvent
    object Delete : AcademicEvent
    object DismissInfoMsg : AcademicEvent
}