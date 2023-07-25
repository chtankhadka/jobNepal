package com.chetan.jobnepal.screens.dashboard


import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

data class DashboardState(
    val videoListResponse: List<UploadNewVideoLink> = listOf(),
    val applyList: List<UploadNewVideoLink> = listOf(),
    val profileUrl: String = "",
    val currentUserName: String = ""
)
