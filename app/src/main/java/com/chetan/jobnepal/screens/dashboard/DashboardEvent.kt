package com.chetan.jobnepal.screens.dashboard

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

sealed interface DashboardEvent{

    class Apply (val value: List<UploadNewVideoLink>) : DashboardEvent

}