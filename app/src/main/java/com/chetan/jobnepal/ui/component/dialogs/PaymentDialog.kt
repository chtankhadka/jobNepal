package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.chetan.jobnepal.R
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal

@Composable
fun PaymentDialog(
    paymentId: String,
    onDismiss: (Boolean) -> Unit
) {

    Dialog(onDismissRequest = { onDismiss(false) }) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(7f)
            ) {
//                Text(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp),
//                    text = buildAnnotatedString {
//                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
//                            append("Note:")
//                        }
//                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)) {
//                            append("Add remarks like this : ")
//                        }
//                        withStyle(
//                            SpanStyle(
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 15.sp,
//                                color = Color.Red
//                            )
//                        ) {
//                            append(paymentId)
//                        }
//                    })
                
                Row(modifier = Modifier.fillMaxWidth()) {
//                    DropdownJobNepal(list = , clicked = )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.khalti_logo),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        modifier = Modifier.size(200.dp),
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.khalti_qr),
                        contentDescription = ""
                    )

                    Divider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.esewa_logo),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        modifier = Modifier.size(200.dp),
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.esewa_qr),
                        contentDescription = ""
                    )
                }

            }
        }


    }
}