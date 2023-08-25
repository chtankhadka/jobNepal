package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@Composable
fun PrivacyPolicyDialog(
    onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss(false)
    }, content = {
        Card {
            Column(modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(

                        )
                    ) {
                        append("This app is based on ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("educational purposes. ")
                    }
                    append(" Due to its early stage there many changes are still in progress. Because of this, it is in testing time. On the other hand, our app called ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
                        append(" Online Form")
                    }
                    append(" asks for some pictures from users so that we can fill their online form This app uses")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
                        append(" One Tap Google SignIn and uses firebase as a database.")
                    }
                })

                Button(onClick = {
                    onDismiss(false)
                }) {
                    Text(text = "Confirm")
                }
            }

        }

    })
}