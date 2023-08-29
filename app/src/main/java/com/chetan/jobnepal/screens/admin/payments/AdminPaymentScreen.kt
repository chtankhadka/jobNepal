package com.chetan.jobnepal.screens.admin.payments

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodRequest
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.textfield.TextFieldJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPaymentScreen(
    navController: NavHostController,
    state: AdminPaymentState,
    onEvent: (event: AdminPaymentEvent) -> Unit
) {

    var selectedLogoUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    var selectedQrUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    var logo by remember {
        mutableStateOf(false)
    }
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    if (logo) {
                        selectedLogoUri = uri
                    } else {
                        selectedQrUri = uri
                    }
                }

            })

    val ctx = LocalContext.current
    var addNewPayment by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), navigationIcon = {
            IconJobNepal(
                onClick = {
                    navController.popBackStack()
                }, icon = Icons.Default.ArrowBack
            )
        }, title = {
            Text(
                text = "Payment", style = MaterialTheme.typography.titleLarge
            )
        })
    }, content = {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            state.infoMsg?.let {
                MessageDialog(message = it, onDismissRequest = {
                    if (onEvent != null && state.infoMsg.isCancellable == true) {
                        onEvent(AdminPaymentEvent.DismissInfoMsg)
                    }
                }, onPositive = { }, onNegative = {})
            }

            if (addNewPayment) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    TextFieldJobNepal(value = state.bankName, onValueChange = {
                        onEvent(AdminPaymentEvent.OnBankNameChange(it))
                    })
                    if (selectedLogoUri == Uri.EMPTY) {
                        Button(onClick = {
                            logo = true
                            singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                        }) {
                            Text(text = "Add Logo")
                        }
                    } else {
                        AsyncImage(model = selectedLogoUri, contentDescription = " logo")
                    }
                    if (selectedQrUri == Uri.EMPTY) {
                        Button(onClick = {
                            logo = false
                            singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                        }) {
                            Text(text = "Add Qr")
                        }
                    } else {
                        AsyncImage(model = selectedQrUri, contentDescription = "Qr")
                    }
                    if (selectedQrUri != Uri.EMPTY && selectedLogoUri != Uri.EMPTY) {
                        Button(onClick = {
                            onEvent(
                                AdminPaymentEvent.OnPaymentDetailsAdd(
                                    data = AddAdminPaymentMethodRequest(
                                        bankName = state.bankName,
                                        bankLogo = selectedLogoUri,
                                        bankQr = selectedQrUri,
                                    )
                                )
                            )
                        }) {

                        }
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            addNewPayment = true
                        },
                    contentScale = ContentScale.Inside,
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    })

}