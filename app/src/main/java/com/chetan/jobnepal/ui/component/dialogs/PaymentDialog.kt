package com.chetan.jobnepal.ui.component.dialogs

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse
import com.chetan.jobnepal.screens.user.dashboard.DashboardEvent

@Composable
fun PaymentDialog(
    paymentMethods: List<AddAdminPaymentMethodResponse>,
    videoId: String,
    onEvent: (event: DashboardEvent) -> Unit,
    onDismiss: (Boolean) -> Unit,

    ) {
    Dialog(onDismissRequest = { onDismiss(false) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var selectedItemIndex by remember {
                mutableIntStateOf(0)
            }
            var selectedImageUri by remember {
                mutableStateOf<Uri>(Uri.EMPTY)
            }
            val singlePhotoPickerLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        uri?.let {
                            selectedImageUri = it
                        }
                    })


            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PaymentDropdown(
                    paymentMethods = paymentMethods,
                    modifier = Modifier
                        .height(50.dp)
                        .weight(0.8f)
                ) {
                    selectedItemIndex = it
                }
                IconButton(onClick = {
                    onDismiss(false)
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            AsyncImage(
                modifier = Modifier.size(200.dp),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Fit,
                model = paymentMethods[selectedItemIndex].bankQr,
                contentDescription = ""
            )
            Divider()
            Row(modifier = Modifier.animateContentSize()) {
                IconButton(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(5.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                    Row {
                        Icon(imageVector = Icons.Default.FileUpload, contentDescription = "Upload File")
                        Text(text = "Upload Receipt ScreenShots")
                    }

                }
                if (selectedImageUri != Uri.EMPTY){
                    TextButton(
                        onClick = {
                        onEvent(DashboardEvent.OnSubmitReceipt(videoId, selectedImageUri))
                    }) {
                        Text(text = "Send")
                    }
                }

            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDropdown(
    paymentMethods: List<AddAdminPaymentMethodResponse>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit

) {

    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 5.dp, vertical = 8.dp),
    ) {
        BasicTextField(
            value = paymentMethods[selectedItemIndex].bankName,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            modifier = Modifier.menuAnchor(),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(70.dp)
                            .height(50.dp),
                        model = paymentMethods[selectedItemIndex].bankLogo,
                        contentDescription = "logo"
                    )
                    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                        innerTextField()
                    }

                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                }
            })
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            paymentMethods.forEachIndexed { index, item ->

                DropdownMenuItem(leadingIcon = {
                    AsyncImage(
                        modifier = Modifier
                            .width(70.dp)
                            .height(50.dp),
                        model = paymentMethods[index].bankLogo,
                        contentDescription = "logo"
                    )
                }, text = {
                    Text(
                        text = item.bankName,
                        fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                    )
                }, onClick = {
                    onClick(index)
                    selectedItemIndex = index
                    expanded = false
                })
                Divider()
            }


        }
    }
}