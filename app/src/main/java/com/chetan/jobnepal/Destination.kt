package com.chetan.jobnepal

open class Destination(open val route : String) {
    object Screen {
        object Start : Destination("dashboard")
        object GoogleSignIn: Destination("google-sign-in")
        object Academic : Destination("academic")
    }
}