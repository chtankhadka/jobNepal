package com.chetan.jobnepal

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chetan.jobnepal.screens.admin.adminbottomsheet.AdminBottomSheetScreen
import com.chetan.jobnepal.screens.admin.adminbottomsheet.AdminBottomSheetViewModel
import com.chetan.jobnepal.screens.admin.dashboard.AdminDashboard
import com.chetan.jobnepal.screens.admin.dashboard.AdminDashboardViewModel
import com.chetan.jobnepal.screens.admin.formrequest.FormRequestScreen
import com.chetan.jobnepal.screens.admin.formrequest.FormRequestViewModel
import com.chetan.jobnepal.screens.admin.notification.AdminNotificationViewModel
import com.chetan.jobnepal.screens.admin.notification.NotificationScreen
import com.chetan.jobnepal.screens.admin.payments.AdminPaymentScreen
import com.chetan.jobnepal.screens.admin.payments.AdminPaymentViewModel
import com.chetan.jobnepal.screens.admin.uploadvideo.UploadVideoScreen
import com.chetan.jobnepal.screens.admin.uploadvideo.UploadVideoViewModel
import com.chetan.jobnepal.screens.onboardscreen.OnBoardScreen
import com.chetan.jobnepal.screens.onboardscreen.OnBoardViewModel
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.LoginWithEmailPasswordScreen
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.LoginWithEmailPasswordViewModel
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.SignupWithEmailPasswordScreen
import com.chetan.jobnepal.screens.sign_in.newlogin.GoogleAuthUiClient
import com.chetan.jobnepal.screens.sign_in.newlogin.SignInScreen
import com.chetan.jobnepal.screens.sign_in.newlogin.SignInViewModel
import com.chetan.jobnepal.screens.user.academic.AcademicScreen
import com.chetan.jobnepal.screens.user.academic.AcademicViewModel
import com.chetan.jobnepal.screens.user.account.ProfileScreen
import com.chetan.jobnepal.screens.user.account.ProfileViewModel
import com.chetan.jobnepal.screens.user.dashboard.DashboardScreen
import com.chetan.jobnepal.screens.user.dashboard.DashboardViewModel
import com.chetan.jobnepal.screens.user.notification.UserNotificationScreen
import com.chetan.jobnepal.screens.user.notification.UserNotificationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
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
            val viewModel = hiltViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = Unit) {
//                navController.cleanNavigate(Destination.Screen.Dashboard.route)
                if (googleAuthUiClient.getSignedInUser() != null) {
                    if (googleAuthUiClient.getSignedInUser()!!.userEmail == "chtankhadka12@gmail.com" || googleAuthUiClient.getSignedInUser()!!.userEmail == "bheshkshetri58@gmail.com") {
//                        navController.cleanNavigate(Destination.Screen.Dashboard.route)
                        navController.cleanNavigate(Destination.Screen.AdminDashboard.route)
                    } else {
//                        navController.cleanNavigate(Destination.Screen.AdminDashboard.route)
                        navController.cleanNavigate(Destination.Screen.Dashboard.route)
                    }
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
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
                if (state.isSignInSuccessful) {
                    navController.navigate("dashboard")
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

        composable("on-board") {
            val viewModel = hiltViewModel<OnBoardViewModel>()
            OnBoardScreen(
                onComplete = {
                    navController.cleanNavigate("sign_in")
                },
                viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent

            )
        }

        composable("sign-with-email-password") {
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            LoginWithEmailPasswordScreen(navController, viewModel)
        }
        composable("signup-with-email-password") {
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            SignupWithEmailPasswordScreen(navController, viewModel)
        }

        composable("dashboard") {
            val viewModel = hiltViewModel<DashboardViewModel>()
            DashboardScreen(
                navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent,
                onClick = {
                    if (it == "logout") {
                        lifecycleScope.launch {
                            googleAuthUiClient.signOut()
                            navController.cleanNavigate("sign_in")
                        }


                    } else if (it == "Nepali") {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        applicationContext.startActivity(intent)
                    }
                }
            )

        }

        composable(Destination.Screen.UserNotification.route) {
            val viewModel = hiltViewModel<UserNotificationViewModel>()
            UserNotificationScreen(
                navController = navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }


        // Admin
        composable(Destination.Screen.AdminDashboard.route) {
            val viewModel = hiltViewModel<AdminDashboardViewModel>()
            AdminDashboard(
                navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent,
                onClick =
                {
                    when (it) {
                        "logout" -> {
                            lifecycleScope.launch {
                                googleAuthUiClient.signOut()
                                navController.cleanNavigate("sign_in")
                            }
                        }

                        "addVideo" -> {
                            navController.navigate(Destination.Screen.UploadVideoScreen.route)
                        }
                    }
                })
        }
        composable(Destination.Screen.AdminSendNotification.route) {
            val viewModel = hiltViewModel<AdminNotificationViewModel>()
            NotificationScreen(
                navController = navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }

        composable(Destination.Screen.AdminBottomSheetNotice.route) {
            val viewModel = hiltViewModel<AdminBottomSheetViewModel>()
            AdminBottomSheetScreen(
                navController = navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }
        composable(Destination.Screen.AdminPayment.route) {
            val viewModel = hiltViewModel<AdminPaymentViewModel>()
            AdminPaymentScreen(
                navController = navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }
        composable(Destination.Screen.UserFormRequestScreen.route) {
            val viewModel = hiltViewModel<FormRequestViewModel>()
            FormRequestScreen(
                navController = navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }





        composable("academic") {
            val viewModel = hiltViewModel<AcademicViewModel>()
            AcademicScreen(
                navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }

        composable("upload-video-screen") {
            val viewModel = hiltViewModel<UploadVideoViewModel>()
            UploadVideoScreen(
                navController,
                state = viewModel.state.collectAsStateWithLifecycle().value,
                onEvent = viewModel.onEvent
            )
        }

        composable(Destination.Screen.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
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