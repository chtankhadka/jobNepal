package com.chetan.jobnepal.screens.admin.dashboard

import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

sealed interface AdminDashboardEvent {
    object DismissInfoMsg : AdminDashboardEvent
}