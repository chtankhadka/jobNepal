package com.chetan.jobnepal.screens.dashboard

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

sealed interface DashboardEvent {

    class ApplyNow(val value: UploadNewVideoLink.DataColl) : DashboardEvent
    class ApplyLetter(val value: UploadNewVideoLink.DataColl) : DashboardEvent
    class ShowApplyDialog(val value: Boolean) : DashboardEvent
    class UpdateCheckedList(val title: String, val value: List<String>) : DashboardEvent
    class SetCheckedList(val value: Boolean): DashboardEvent

}