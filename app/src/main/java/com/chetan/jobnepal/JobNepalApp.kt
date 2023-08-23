package com.chetan.jobnepal

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JobNepalApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initOnesignal()
        MobileAds.initialize(this){

        }
    }


    private fun initOnesignal(){
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        //OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(BuildConfig.OneSignal_API_Key)


        // promptForPublishNotification will show the native Android notification permission prompt
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)

        OneSignal.promptForPushNotifications()
    }

}