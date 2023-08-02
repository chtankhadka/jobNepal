package com.chetan.jobnepal.screens.dashboard

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

sealed interface DashboardEvent {
    class ApplyNow(val value: UploadNewVideoLink.DataColl) : DashboardEvent
    class ApplyLater(val value: UploadNewVideoLink.DataColl) : DashboardEvent
    class ShowApplyDialog(val value: Boolean) : DashboardEvent
    class DeleteAppliedData(val value: String) : DashboardEvent
    class UpdateCheckedList(
        val title: String,
        val value: List<String>,
        val selectedLevels: List<String>
    ) : DashboardEvent

    object DismissInfoMsg : DashboardEvent


}