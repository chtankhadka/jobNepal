package com.chetan.jobnepal.utils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun VibratingIcon(
    notificationsActive: ImageVector,
    isVibrating: Boolean = false,
    onClick: () -> Unit
) {


    val shakeAnimationSpec = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val shakeModifier = Modifier
        .graphicsLayer {
            if (isVibrating) {
                val shakeX = shakeAnimationSpec.value * 4
                val shakeY = shakeAnimationSpec.value * 1
                translationX = shakeX
                translationY = shakeY
            }
        }
        .size(32.dp)

    IconButton(
        onClick = {
            onClick()
                  },
        modifier = shakeModifier
    ) {
        Icon(
            tint = if (true) {

                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.background
            },
            imageVector = notificationsActive,
            contentDescription = "Close"
        )
    }
}