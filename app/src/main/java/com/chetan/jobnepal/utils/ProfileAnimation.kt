package com.chetan.jobnepal.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chetan.jobnepal.R
import com.chetan.jobnepal.ui.component.PhotoPickerJobNepal
import kotlinx.coroutines.delay
import java.io.Serializable
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileAnimation(size: Dp, padding: Dp, enableEdit: Boolean, uri: Serializable?) {
    var sizeState by remember {
        mutableStateOf(IntSize.Zero)
    }
    var cameraPosition by remember {
        mutableFloatStateOf(0f)
    }
    var singlePhotoPicker by remember {
        mutableStateOf(false)
    }
    var singlePhotoUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    var multiplePhotoUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    if (enableEdit){
        if (singlePhotoPicker) {
            PhotoPickerJobNepal(
                singlePhoto = true,
                picUri = {
                    singlePhotoUri = it
                },
                picUris = { multiplePhotoUris = it },
            ) { singlePhotoPicker = false }
        }

        LaunchedEffect(Unit) {
            while (cameraPosition < 0.8f) {
                delay(100)
                cameraPosition += 0.01f
            }
        }
        Box(
            modifier = Modifier
                .onSizeChanged {
                    sizeState = it
                }
                .background(Color.Transparent)
                .padding(padding),
            Alignment.Center
        ) {
            val r = size.value / 2
            val a = cos(cameraPosition) * r
            val b = sin(cameraPosition) * r
            AsyncImage(
                modifier = Modifier
                    .width(size)
                    .height(size)
                    .clip(CircleShape)
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
                        shape = CircleShape
                    ),

                contentScale = ContentScale.Crop,
                model = uri?: singlePhotoUri,
                contentDescription = "Profile",
                alignment = Alignment.Center
            )
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .offset(a.dp, b.dp)
                    .clickable {
                        singlePhotoPicker = true
                    },
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "shopping"
            )
        }
    }else{
        AsyncImage(
            modifier = Modifier
                .width(size)
                .height(size)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
                    shape = CircleShape
                ),

            contentScale = ContentScale.Crop,
            model = uri?: singlePhotoUri,
            contentDescription = "Profile",
            alignment = Alignment.Center
        )
    }

}
