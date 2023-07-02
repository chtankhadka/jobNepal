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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.data.Resource

@Composable
fun SignupWithEmailPassword(
    navController: NavHostController,
    viewModel: LoginWithEmailPasswordViewModel
){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }

    val signupFlow = viewModel.signupFlow.collectAsState()
    Column() {
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

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
        TextField(
            value = repassword,
            onValueChange = {
                repassword = it
            } ,
            label = { Text(text = "Re - Password")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Button(onClick = {
            viewModel.signup(name,email,password)
        }) {
            Text(text = "Signup")
        }
    }
    signupFlow.value?.let {
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