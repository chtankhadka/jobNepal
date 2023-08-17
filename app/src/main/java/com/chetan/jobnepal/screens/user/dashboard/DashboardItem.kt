package com.chetan.jobnepal.screens.user.dashboard


import JobsApplyDialog
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.YoutubeSearchedFor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal
import com.chetan.jobnepal.utils.youtubePlayer.WebContent

@Composable
fun DashboardItem(
    list: UploadNewVideoLink.DataColl,
    isApplied: Boolean,
    onEvent: (event: DashboardEvent) -> Unit,
    state: DashboardState
) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    val id = remember {
        mutableStateOf(list.id)
    }
    if (state.showApplyDialog) {
        JobsApplyDialog(
            listOfJobs = state.jobsForDialog,
            onEvent = onEvent,
            onApplied = {
                onEvent(DashboardEvent.ApplyNow(id.value))
                onEvent(DashboardEvent.ShowApplyDialog(false))
            },
            onDismissListener = {
                onEvent(DashboardEvent.ShowApplyDialog(false))
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        var sizeWidth = 0f
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .drawBehind {
                    sizeWidth = size.width
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
            Text(text = list.title)
            DropdownJobNepal(
                listOf(
                    Triple("Full Guid", Icons.Default.YoutubeSearchedFor, true),
                    Triple("Apply Now", Icons.Default.FastForward, !isApplied)
                )
            ) {
                when (it) {
                    "Apply Now" -> {
                        onEvent(DashboardEvent.SelectVideo(list.id))
                        onEvent(DashboardEvent.JobsForDialog(list))

                        onEvent(DashboardEvent.ShowApplyDialog(true))
                    }

                    else -> {
                        println(list.id)
                    }
                }


            }
        }
        WebContent(videoId = list.videoLink, modifier = Modifier.height(380.dp))
//        AsyncImage(
//            modifier = Modifier.fillMaxWidth(),
//            contentScale = ContentScale.FillWidth,
//            model = list.videoLink,
//            contentDescription = "details",
//            alignment = Alignment.Center
//        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "240",
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Icon(
                    imageVector = if (!isVisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        isVisible = !isVisible
                    }
                )
            }
            Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
            if (isVisible) {
                Text(
                    text = list.academicList.toString(),
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }


//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ThumbUp,
//                    tint = if (true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
//                    contentDescription = "Like"
//                )
//                Text(
//                    text = "240",
//                    modifier = Modifier.padding(horizontal = 2.dp),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                )
//                Icon(
//                    imageVector = Icons.Default.ThumbDown,
//                    tint = if (true) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
//                    contentDescription = "Dislike"
//                )
//                Text(
//                    text = "50",
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                )
//            }
//            Card() {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Comment,
//                        contentDescription = "",
//                    )
//                    Text(
//                        text = "1K",
//                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                    )
//                }
//            }
//            Card(
//                enabled = !isApplied,
//                onClick = {
//                    onEvent(DashboardEvent.Apply(data))
//
//                },
//                colors = CardDefaults.cardColors(Color.Transparent),
//                ) {
//                Text(
//                    text = if (isApplied) "Applied" else "Apply",
//                    modifier = Modifier.padding(horizontal = 2.dp),
//                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                )
//
//            }

        }
    }
}