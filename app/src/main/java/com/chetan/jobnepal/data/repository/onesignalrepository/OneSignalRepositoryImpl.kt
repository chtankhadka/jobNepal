package com.chetan.jobnepal.data.repository.onesignalrepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.oneSignal.PushNotificationRequest
import retrofit2.HttpException
import javax.inject.Inject

class OneSignalRepositoryImpl @Inject constructor(
    private val oneSignal : OneSignalRepository
) : OneSignalRepository {
    override suspend fun pushNotification(requestBody: PushNotificationRequest): Resource<Any> {
        return try {
            val response = oneSignal.pushNotification(requestBody)
            Resource.Success(response)
        }catch (e: HttpException){
            e.printStackTrace()
            Resource.Failure(e)
        }
        catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}