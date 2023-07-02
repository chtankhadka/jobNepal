package com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.data.Resource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginWithEmailPassword(
    navController: NavHostController,
    viewModel: LoginWithEmailPasswordViewModel?
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember { mutableStateOf("") }

    val loginFlow = viewModel?.loginFlow?.collectAsState()

    Column() {
        TextField(
            value = email,
            onValueChange ={
                email = it
            } ,
            label = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
            )
        
        TextField(
            value = password,
            onValueChange = {
                password = it
            } ,
            label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
            )
        Button(onClick = {
            viewModel?.login(email, password)
        }) {
            Text(text = "Login")
        }
        Button(onClick = {
            navController.navigate(Destination.Screen.SignupWithEmailPassword.route)
        }) {
            Text(text = "SignUp")
        }

    }
    loginFlow?.value?.let {
        when(it){
            is Resource.Failure -> Toast.makeText(LocalContext.current,it.exception.message, Toast.LENGTH_LONG).show()
            Resource.Loading -> CircularProgressIndicator()
            is Resource.Success -> {
                LaunchedEffect(Unit ){
                    navController.navigate(Destination.Screen.Start.route){
                        popUpTo(Destination.Screen.Start.route){inclusive = true}
                    }
                }

            }
        }
    }

}