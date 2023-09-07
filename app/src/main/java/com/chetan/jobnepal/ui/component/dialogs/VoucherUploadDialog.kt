package com.chetan.jobnepal.ui.component.dialogs

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.chetan.jobnepal.screens.user.dashboard.DashboardEvent

@Composable
fun VoucherUploadDialog(
    onEvent: (event: DashboardEvent) -> Unit, vid: String, hide: (Boolean) -> Unit

) {

    var imageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val photoPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                }

            })


    Dialog(onDismissRequest = {

    }) {
        Card(
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Text(
                color = Color.White,
                text = "Upload Paid Voucher",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary).padding(10.dp)
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                if (imageUri == Uri.EMPTY) {
                    IconButton(onClick = {
                        photoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = "")
                    }
                } else {
                    Icon(imageVector = Icons.Default.UploadFile, contentDescription ="" )
                    Text(
                        text = "Are you sure want to upload ?",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


            }
            Row(modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                if (imageUri != Uri.EMPTY) {
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onEvent(DashboardEvent.UploadPaidVoucher(imgUri = imageUri, vid = vid))
                        hide(true)
                    }) {
                        Text(text = "Upload")
                    }
                }
                Button(modifier = Modifier.weight(1f), onClick = {
                    hide(true)
                }) {
                    Text(text = "Cancel")
                }
            }


        }
    }
}
