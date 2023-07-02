package com.chetan.jobnepal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chetan.jobnepal.screens.academic.Academic
import com.chetan.jobnepal.screens.dashboard.DashboardScreen
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.LoginWithEmailPassword
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.LoginWithEmailPasswordViewModel
import com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication.SignupWithEmailPassword
import com.chetan.jobnepal.screens.sign_in.onetapauthentication.ProfileScreen
import com.chetan.jobnepal.screens.sign_in.onetapauthentication.UserData

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    onBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "sign-with-email-password"
    ) {
        composable("sign-with-email-password"){
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            LoginWithEmailPassword(navController,viewModel)
        }
        composable("signup-with-email-password"){
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            SignupWithEmailPassword(navController,viewModel)
        }

        composable("dashboard") {
            val viewModel = hiltViewModel<LoginWithEmailPasswordViewModel>()
            DashboardScreen(navController, viewModel)
            {
                navController.navigate(Destination.Screen.GoogleSignIn.route)
            }
        }
        composable("google-sign-in"){
            ProfileScreen(userData = UserData("h","","")) {
                
            }
        }
        composable("academic"){
            Academic(navController)
        }
        
    }
}