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
import com.chetan.jobnepal.screens.sign_in.GoogleSignInClient
import com.chetan.jobnepal.screens.sign_in.ProfileScreen
import com.chetan.jobnepal.screens.sign_in.SignInScreen
import com.chetan.jobnepal.screens.sign_in.SignInViewModel
import com.chetan.jobnepal.ui.theme.JobNepalTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleSignInClient(
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
//                    NavHost(navController = navController, startDestination = "sign_in") {
//                        composable("sign_in") {
//                            val viewModel = viewModel<SignInViewModel>()
//                            val state by viewModel.state.collectAsStateWithLifecycle()
//
//                            LaunchedEffect(key1 = Unit) {
//                                if (googleAuthUiClient.getSignedInUser() != null) {
//                                    Toast.makeText(applicationContext,"clicked",Toast.LENGTH_LONG).show()
//                                    navController.navigate("profile")
//                                }
//                            }
//                            val launcher = rememberLauncherForActivityResult(
//                                contract = ActivityResultContracts.StartIntentSenderForResult(),
//                                onResult = { result ->
//                                    if (result.resultCode == RESULT_OK) {
//                                        lifecycleScope.launch {
//                                            val singInResult = googleAuthUiClient.signInWithIntent(
//                                                intent = result.data ?: return@launch
//                                            )
//                                            viewModel.onSignInResult(singInResult)
//                                        }
//                                    }
//
//                                }
//                            )
//                            LaunchedEffect(key1 = state.isSigInSuccessful) {
//                                if (state.isSigInSuccessful) {
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Sign in successful ",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                    navController.navigate("profile")
//                                    viewModel.resetState()
//                                }
//                            }
//
//                            SignInScreen(state = state, onSignInClick = {
//                                lifecycleScope.launch {
//                                    val signIntentSender = googleAuthUiClient.signIn()
//                                    launcher.launch(
//                                        IntentSenderRequest.Builder(
//                                            signIntentSender ?: return@launch
//                                        ).build()
//                                    )
//                                }
//                            }
//                            )
//                        }
//                        composable("profile") {
//                            ProfileScreen(
//                                userData = googleAuthUiClient.getSignedInUser(),
//                                onSignOut = {
//                                    lifecycleScope.launch {
//                                        googleAuthUiClient.signOut()
//                                        Toast.makeText(
//                                            applicationContext, "Signed Out",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                        navController.popBackStack()
//                                    }
//                                }
//                            )
//                        }
//                    }

                  AppNavHost(
                      modifier = Modifier,
                      navController = navController,
                      onBack = {
                          if (!navController.popBackStack()){
                              exitOrAsk()
                          }
                      }
                  )

                }
            }
        }
    }
    var doubleBackToExitPressedOnce = false
    private fun exitOrAsk(){
        if (doubleBackToExitPressedOnce){
            finish()
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this,"Press Back again to exit",
        Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        },2000)
    }

}