package com.chetan.jobnepal.ui.component.animation

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun YouCannotClickMe(
    size: Float,
    buttonWidth: Int,
    buttonHeight: Int,
    text: String,
    boxColor: Color,
    buttonColor: Color,
    onClick: () -> Unit,
    enable: Boolean = true
) {
    var oneDp by remember { mutableFloatStateOf(0f) }
    var boxSize by remember { mutableIntStateOf(0) }
    var oneAxix by remember { mutableFloatStateOf(0f) }
    var xAxis by remember { mutableFloatStateOf(0f) }
    var yAxis by remember { mutableFloatStateOf(0f) }

    var clickOrDrag by remember {
        mutableStateOf(false)
    }

    BoxWithConstraints(
        modifier = Modifier
            .size(size.dp)
            .background(boxColor)
            .pointerInteropFilter { event ->
                val offset = Offset(event.x, event.y)
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        clickOrDrag = true
                        xAxis = offset.x
                        yAxis = offset.y
                    }

                    android.view.MotionEvent.ACTION_MOVE -> {
                        val delta = Offset(event.x, event.y)
                        clickOrDrag = true
                        xAxis = delta.x
                        yAxis = delta.y
                    }

                    android.view.MotionEvent.ACTION_UP -> {
                        clickOrDrag = false
                        xAxis = boxSize / 2f
                        yAxis = boxSize / 2f
                    }
                }
                true
            }
            .onGloballyPositioned { coordinates ->
                boxSize = coordinates.size.width
                oneDp = boxSize.toFloat() / size
                oneAxix = (size / boxSize.toFloat())
            }
    ) {
        val transition = updateTransition(clickOrDrag, label = "")
        val offsetX by transition.animateFloat(label = "") { clicked ->
            if (clicked && enable) {
                val targetX = if (xAxis < (boxSize / 2f)) {
                    (boxSize - xAxis) * oneAxix + 25
                } else {
                    (boxSize - xAxis) * oneAxix - 75
                }
                targetX.coerceIn(0f, (boxSize - 50) * oneAxix)
            } else {
                (boxSize / 2f) * oneAxix
            }
        }
        val offsetY by transition.animateFloat(label = "") { clicked ->
            if (clicked && enable) {
                val targetY = if (yAxis < (boxSize / 2f)) {
                    (boxSize - yAxis) * oneAxix + 15
                } else {
                    (boxSize - yAxis) * oneAxix - 45
                }
                targetY.coerceIn(0f, (boxSize - 30) * oneAxix)
            } else {
                (boxSize / 2f) * oneAxix
            }
        }


        Button(
            onClick = {
                if (enable) {
                    onClick()
                }

            },
            modifier = Modifier
                .offset(offsetX.dp - 25.dp, offsetY.dp - 15.dp)
                .size(width = buttonWidth.dp, height = buttonHeight.dp),
            colors = ButtonDefaults.buttonColors(buttonColor)
        ) {
            Text(text = text)
        }
    }

}


@Composable
@Preview
fun showAnimation() {
    YouCannotClickMe(
        size = 300f,
        buttonWidth = 70,
        buttonHeight = 40,
        text = "Hi",
        boxColor = Color.Green,
        buttonColor = Color.Red,
        enable = true,
        onClick = { })
}





