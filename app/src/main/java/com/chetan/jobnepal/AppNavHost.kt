package com.chetan.jobnepal

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chetan.jobnepal.screens.academic.Academic
import com.chetan.jobnepal.screens.admin.uploadvideo.UploadVideoScreen
import com.chetan.jobnepal.screens.admin.uploadvideo.UploadVideoViewModel
import com.chetan.jobnepal.screens.dashboard.DashboardScreen
import com.chetan.jobnepal.screens.onboardscreen.OnBoardScreen
import com.chetan.jobnepal.screens.onboardscreen.OnBoardViewModel
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.LoginWithEmailPasswordScreen
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.LoginWithEmailPasswordViewModel
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.SignupWithEmailPasswordScreen
import com.chetan.jobnepal.screens.sign_in.newlogin.GoogleAuthUiClient
import com.chetan.jobnepal.screens.sign_in.newlogin.SignInScreen
import com.chetan.jobnepal.screens.sign_in.newlogin.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    onBack: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    lifecycleScope: LifecycleCoroutineScope,
    applicationContext: Context
) {
    val onBoardViewModel = hiltViewModel<OnBoardViewModel>()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (onBoardViewModel.preference.onBoardCompleted) "sign_in" else "on-board"
    ) {
        composable("sign_in") {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = Unit) {
                if(googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate("dashboard")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate("profile")
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable("on-board"){
            val viewModel= hiltViewModel<OnBoardViewModel>()
            OnBoardScreen(
                onComplete = {
                    navController.cleanNavigate("sign_in")
                },
                viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent

            )
        }

        composable("sign-with-email-password"){
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            LoginWithEmailPasswordScreen(navController,viewModel)
        }
        composable("signup-with-email-password"){
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            SignupWithEmailPasswordScreen(navController,viewModel)
        }

        composable("dashboard") {
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            DashboardScreen(navController, viewModel)
            {
                navController.navigate(Destination.Screen.GoogleSignIn.route)
            }
        }
//        composable("google-sign-in"){
//            ProfileScreen(userData = UserData("h","","")) {
//
//            }
//        }
        composable("academic"){
            Academic(navController)
        }

        composable("upload-video-screen"){
            val viewModel = hiltViewModel<UploadVideoViewModel>()
            UploadVideoScreen(
                navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }
        
    }
}

fun NavHostController.cleanNavigate(
    toRoute: String,
    popUpTo: String? = currentDestination?.route
) {
    navigate(route = toRoute) {
        popUpTo?.let {
            popUpTo(it) {
                inclusive = true
            }
        }
    }
}