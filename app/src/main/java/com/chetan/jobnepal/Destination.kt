package com.chetan.jobnepal

open class Destination(open val route : String) {
    object Screen {

        // admin
        object AdminDashboard: Destination("admin-dashboard")
        object UploadVideoScreen : Destination("upload-video-screen")

        object AdminSendNotification: Destination("admin-send-notification")

        //user

        object OnBoard : Destination("on-board")
        object Dashboard : Destination("dashboard")
        object SignupWithEmailPassword: Destination("signup-with-email-password")
        object SignWithEmailPassword: Destination("sign-with-email-password")
        object GoogleSignIn: Destination("google-sign-in")
        object Academic : Destination("academic")

        object userNotification: Destination("user-notification")


        object ProfileScreen : Destination("profile-screen")
    }
}