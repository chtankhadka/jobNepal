package com.chetan.jobnepal.screens.sign_in.emailandpasswordauthentication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.ui.theme.JobNepalTheme


@Composable
fun LoginWithEmailPasswordScreen(
    navController: NavHostController?,
    viewModel: LoginWithEmailPasswordViewModel?
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember { mutableStateOf("") }

    val loginFlow = viewModel?.loginFlow?.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
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
            },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = {
            viewModel?.login(email, password)
        }) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.outline)){
                    append("Don't have an account?")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
                    append("Create an account")
                }
            }
        )
        Button(onClick = {
            navController?.navigate(Destination.Screen.SignupWithEmailPassword.route)
        }) {
            Text(text = "SignUp")
        }

    }
    loginFlow?.value?.let {
        when (it) {
            is Resource.Failure -> Toast.makeText(
                LocalContext.current,
                it.exception.message,
                Toast.LENGTH_LONG
            ).show()

            Resource.Loading -> CircularProgressIndicator()
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController?.navigate(Destination.Screen.Dashboard.route) {
                        popUpTo(Destination.Screen.Dashboard.route) { inclusive = true }
                    }
                }

            }
        }
    }

}

@Preview
@Composable
fun showThis(){
    JobNepalTheme() {
        LoginWithEmailPasswordScreen(
            navController = null ,
            viewModel = null)

    }
}