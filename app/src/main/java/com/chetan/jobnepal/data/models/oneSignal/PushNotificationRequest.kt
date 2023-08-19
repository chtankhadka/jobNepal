package com.chetan.jobnepal.data.models.oneSignal

import com.chetan.jobnepal.BuildConfig

data class PushNotificationRequest(
    val app_id :String = BuildConfig.OneSignal_API_Key,
    val included_segments: List<String> = listOf("Active Users"),
    val contents: Map<String, String>,
    val name: String
)
