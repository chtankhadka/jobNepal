package com.chetan.jobnepal.ui.component

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CustomTooltipShape() : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            // Draw your custom path here
            path = drawTooltipPath(size = size)
        )
    }
    fun drawTooltipPath(size: Size): Path {
        return Path().apply {
            reset()
            val roundStart = size.height/3
            arcTo(rect = Rect(
                0f,
                0f,
                roundStart * 2,
                roundStart * 2),
                -90f, -180f, true)
            lineTo(size.width/2 - roundStart / 2, size.height * 2/3)
            lineTo(size.width/2, size.height)
            lineTo(size.width/2 + roundStart / 2, size.height * 2/3)
            lineTo(size.width - roundStart, size.height * 2/3)
            arcTo(rect = Rect(size.width - roundStart * 2, 0f,
                size.width, size.height * 2/3),
                90f, -180f, true)
            lineTo(roundStart, 0f)
            close()
        }
    }

}