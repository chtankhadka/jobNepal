package com.chetan.jobnepal.ui.component.bottomsheet

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhotoCameraFront
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chetan.jobnepal.R
import com.chetan.jobnepal.utils.file.createFileInCache
import com.chetan.jobnepal.utils.file.getUriForFile
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

enum class PickFileFrom(val textId: Int, val icon: ImageVector) {
    CAMERA(R.string.take_a_selfie, Icons.Default.PhotoCameraFront),
    GALLERY(R.string.upload_from_gallery, Icons.Default.PhoneAndroid),
    STORAGE(R.string.upload_from_storage, Icons.Default.Storage)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PickFileBottomSheet(
    sheetState: SheetState
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState
    )
    {
        Column(
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
            val targetFile = remember {
                ctx.createFileInCache("image_capture.jpg")
            }
            val takePictureLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { isTaken ->
                    if (isTaken) {
                        val file1 =
                            ctx.createFileInCache("image_capture_${System.currentTimeMillis()}.jpg")
                        targetFile.copyTo(file1)
                        scope.launch {
                            sheetState.hide()
                        }
                    }
                }

            listOf(PickFileFrom.CAMERA, PickFileFrom.GALLERY).forEach { selectFileFrom ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (selectFileFrom) {
                                PickFileFrom.CAMERA -> {
                                    val targetFileUri = ctx.getUriForFile(targetFile)
//                                                if (cameraPermissionState.status.isGranted) takePictureLauncher.launch(
//                                                    targetFileUri
//                                                )
//                                                else cameraPermissionState.launchPermissionRequest()
                                }
//
//                                            PickFileFrom.GALLERY -> {
//                                                pickPictureLauncher.launch("image/*")
//                                            }
//
//                                            PickFileFrom.STORAGE -> {
//                                                pickPictureLauncher.launch("*/*")
//                                            }
                                else -> {}
                            }
                        }
                        .padding(vertical = 9.dp, horizontal = 25.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        selectFileFrom.icon,
                        modifier = Modifier.size(20.dp),
                        contentDescription = stringResource(id = selectFileFrom.textId)
                    )
                    Text(
                        text = stringResource(id = selectFileFrom.textId),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

    }


}
