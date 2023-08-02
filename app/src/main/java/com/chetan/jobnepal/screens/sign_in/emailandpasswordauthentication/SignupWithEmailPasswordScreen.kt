package com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.ui.component.animation.YouCannotClickMe

@Composable
fun SignupWithEmailPasswordScreen(
    navController: NavHostController,
    viewModel: LoginWithEmailPasswordViewModel
){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var enableValidation by remember {
        mutableStateOf(false)
    }

    val signupFlow = viewModel.signupFlow.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    ) {
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
        Spacer(modifier = Modifier.height(5.dp))
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
        Spacer(modifier = Modifier.height(5.dp))
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
        Spacer(modifier = Modifier.height(5.dp))
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

        Spacer(modifier = Modifier.height(5.dp))

        enableValidation = password != repassword

        var show by remember {
            mutableStateOf(false)
        }
        YouCannotClickMe(
            size = 300f,
            buttonWidth = 100,
            buttonHeight = 40,
            text = "Signup",
            boxColor = Color.White,
            buttonColor = MaterialTheme.colorScheme.primary,
            onClick = {
                show = true
                if (!enableValidation){
                    viewModel.signup(name,email,password)
                }

            },
            enable = enableValidation
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (show){
            Toast.makeText(LocalContext.current,"hi",Toast.LENGTH_LONG).show()
        }



    }
    signupFlow.value?.let {
        when(it){
            is Resource.Failure -> Toast.makeText(LocalContext.current,it.exception.message, Toast.LENGTH_LONG).show()
            Resource.Loading -> CircularProgressIndicator()
            is Resource.Success -> {
                LaunchedEffect(Unit ){
                    navController.navigate(Destination.Screen.Dashboard.route){
                        popUpTo(Destination.Screen.Dashboard.route){inclusive = true}
                    }
                }

            }
        }
    }
}