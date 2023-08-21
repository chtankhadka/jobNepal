package com.chetan.jobnepal.screens.sign_in.newlogin

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.chetan.jobnepal.R


@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog) {
        Dialog(onDismissRequest = {
            showDialog = false
        },
            content = {
                Text(text = "Very Good..")
                Button(onClick = { showDialog = false }) {
                    Text(text = "Confirm")
                }
            })

    }
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
            decorateText(text = "One", fontSize = 50.sp)
            decorateText(text = "More Steep", fontSize = 50.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                    }
                )
                Text(
                    text = "Terms and Conditions",
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )


            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(if (isChecked) 1f else 0.3f)
                    .animateContentSize(
                        animationSpec = tween(durationMillis = 500,
                            easing = LinearOutSlowInEasing),
                    ),
                onClick = { onSignInClick() },
                enabled = isChecked,

                ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(

                    ),

                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Login")
                    Icon(
                        imageVector = Icons.Default.Login,
                        contentDescription = "",
                    )
                }

            }
        }
    }
}

@Composable
fun decorateText(text: String, fontSize: TextUnit) {
    Text(
        textAlign = TextAlign.Center,
        text = text,
        style = LocalTextStyle.current.merge(
            TextStyle(
                fontSize = fontSize,
                brush = Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.error,
                        MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ),
                drawStyle = Stroke(
                    width = 8f, join = StrokeJoin.Round,
                    pathEffect = PathEffect.cornerPathEffect(fontSize.value)
                )
            )
        )
    )
}