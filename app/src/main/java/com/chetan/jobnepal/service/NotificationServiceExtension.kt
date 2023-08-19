package com.chetan.jobnepal.service

import android.content.Context
import android.widget.Toast
import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse
import com.chetan.jobnepal.di.HiltEntryPoint
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationServiceExtension : OneSignal.OSRemoteNotificationReceivedHandler {
    override fun remoteNotificationReceived(context: Context?, notificationReceivedEvent: OSNotificationReceivedEvent?) {
        try {
            println("I am called")
            val notification = notificationReceivedEvent?.notification
            val hiltEntryPoint =
                EntryPointAccessors.fromApplication(context!!, HiltEntryPoint::class.java)
            val firestoreRepository = hiltEntryPoint.firestoreRepository()
            CoroutineScope(SupervisorJob()).launch {
                    firestoreRepository.saveNotification(StoreNotificationRequestResponse(
                        body = notification?.body?:"",
                        time = notification?.sentTime.toString(),
                    ))

            }
        }catch (e: Exception){
            e.printStackTrace()
        }



    }
}