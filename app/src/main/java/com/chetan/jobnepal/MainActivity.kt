package com.chetan.jobnepal

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.chetan.jobnepal.base.PreferenceActivity
import com.chetan.jobnepal.screens.sign_in.newlogin.GoogleAuthUiClient
import com.chetan.jobnepal.ui.theme.JobNepalTheme
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : PreferenceActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobNepalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.error),
                    color = MaterialTheme.colorScheme.error
                ) {
                    val navController = rememberNavController()
                    AppNavHost(
                        modifier = Modifier,
                        navController = navController,
                        onBack = {
                            if (!navController.popBackStack()) {
                                exitOrAsk()
                            }
                        },
                        googleAuthUiClient,
                        lifecycleScope,
                        applicationContext
                    )

                }
            }
        }
    }

    var doubleBackToExitPressedOnce = false
    private fun exitOrAsk() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(
            this, "Press Back again to exit",
            Toast.LENGTH_SHORT
        ).show()
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

}