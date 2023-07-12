package com.chetan.jobnepal

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chetan.jobnepal.screens.sign_in.newlogin.GoogleAuthUiClient
import com.chetan.jobnepal.screens.sign_in.newlogin.ProfileScreen
import com.chetan.jobnepal.screens.sign_in.newlogin.SignInScreen
import com.chetan.jobnepal.screens.sign_in.newlogin.SignInViewModel
import com.chetan.jobnepal.ui.theme.JobNepalTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

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