package com.chetan.jobnepal.data.models.oneSignal

import com.chetan.jobnepal.BuildConfig

data class PushNotificationRequest(
    val app_id :String = BuildConfig.OneSignal_API_Key,
    val include_subscription_ids : List<String> = listOf("ccbaeaf1-beba-4b98-8138-03f689350769"),
//    val included_segments: List<String> = listOf("Active Users"),
    val contents: Map<String, String>,
    val name: String
)
