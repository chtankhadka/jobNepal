package com.chetan.jobnepal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chetan.jobnepal.screens.dashboard.DashboardScreen
import com.chetan.jobnepal.screens.sign_in.ProfileScreen
import com.chetan.jobnepal.screens.sign_in.UserData

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    onBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable("dashboard") {
            DashboardScreen(navController)
            {
                navController.navigate(Destination.Screen.GoogleSignIn.route)
            }
        }
        composable("google-sign-in"){
            ProfileScreen(userData = UserData("h","","")) {
                
            }
        }
        
    }
}