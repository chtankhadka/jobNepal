package com.chetan.jobnepal.screens.academic

import android.net.Uri

sealed interface AcademicEvent{
    class UploadAttachement(var value: List<Uri>) : AcademicEvent
    class LevelSelected(var value: String) : AcademicEvent
    class ShowDialog(var value: Boolean) : AcademicEvent
    class ShowEdit(var value: Boolean) : AcademicEvent
    class Delete(var value: String, var name: List<String>) : AcademicEvent
}