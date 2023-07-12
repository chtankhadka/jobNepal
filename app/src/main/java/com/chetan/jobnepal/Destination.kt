package com.chetan.jobnepal

open class Destination(open val route : String) {
    object Screen {
        object OnBoard : Destination("on-board")
        object Start : Destination("dashboard")
        object SignupWithEmailPassword: Destination("signup-with-email-password")
        object SignWithEmailPassword: Destination("sign-with-email-password")
        object GoogleSignIn: Destination("google-sign-in")
        object Academic : Destination("academic")
        object UploadVideoScreen : Destination("upload-video-screen")
    }
}