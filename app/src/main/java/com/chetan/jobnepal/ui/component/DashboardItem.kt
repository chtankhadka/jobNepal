package com.chetan.jobnepal.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.YoutubeSearchedFor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.screens.dashboard.DashboardEvent
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItem(
    data: UploadNewVideoLink.DataColl,
    onEvent: (event: DashboardEvent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .drawBehind {
                    drawLine(
                        color = Color.White, // Set the desired color of the border
                        start = Offset(0f, size.height), // Starting point at the bottom-left corner
                        end = Offset(
                            size.width,
                            size.height
                        ), // Ending point at the bottom-right corner
                        strokeWidth = 1.dp.toPx(), // Set the desired width of the border
                    )
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(text = data.title)
            DropdownJobNepal(listOf(
                "Full Guid" to Icons.Default.YoutubeSearchedFor,
                "Apply later" to Icons.Default.Alarm
                )){}
        }
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = data.videoLink,
            contentDescription = "details",
            alignment = Alignment.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .drawBehind {
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    tint = if (true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Like"
                )
                Text(
                    text = "240",
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Icon(
                    imageVector = Icons.Default.ThumbDown,
                    tint = if (true) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Dislike"
                )
                Text(
                    text = "50",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Card() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Comment,
                        contentDescription = "",
                    )
                    Text(
                        text = "1K",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
            Card(
                onClick = {
                    onEvent(DashboardEvent.Apply(data))
                }) {
                Text(
                    text = "Apply",
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

            }

        }
    }
}
