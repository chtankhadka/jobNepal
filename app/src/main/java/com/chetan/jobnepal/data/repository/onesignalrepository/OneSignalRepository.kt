package com.chetan.jobnepal.data.repository.onesignalrepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.oneSignal.PushNotificationRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OneSignalRepository {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Authorization: Basic N2Q4NjAyNTEtZTdlYy00MmYzLWEwNjktYzQ2OTY2MTg4NWUx"
    )
    @POST("notifications")
    suspend fun pushNotification(
        @Body requestBody: PushNotificationRequest
    ): Resource<Any>

}