package com.chetan.jobnepal.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconJobNepal(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    background: Color = MaterialTheme.colorScheme.onPrimary
    ){
    Box(
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(20))
            .clip(RoundedCornerShape(20))
            .background(background)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = "Close"
        )
    }
}