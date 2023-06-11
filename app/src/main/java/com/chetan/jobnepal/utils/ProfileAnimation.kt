package com.chetan.jobnepal.utils

import android.annotation.SuppressLint
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.chetan.jobnepal.R
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileAnimation(size: Dp, padding: Dp) {
    var sizeState by remember {
        mutableStateOf(IntSize.Zero)
    }
    var cameraPosition by remember {
        mutableFloatStateOf(0f)
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
        Image(
            modifier = Modifier
                .width(size)
                .height(size)
                .clip(CircleShape)
                .border(border = BorderStroke(2.dp,MaterialTheme.colorScheme.onPrimary), shape = CircleShape),

            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile",
            alignment = Alignment.Center
        )
        Icon(
            modifier = Modifier
                .size(32.dp)
                .offset(a.dp, b.dp)
                .clickable {

                },
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = "shopping"
        )
    }
}
