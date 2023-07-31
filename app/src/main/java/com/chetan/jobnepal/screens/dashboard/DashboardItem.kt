package com.chetan.jobnepal.screens.dashboard


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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal

@Composable
fun DashboardItem(
    index: Int,
    isApplied: Boolean,
    onEvent: (event: DashboardEvent) -> Unit,
    state: DashboardState
) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    if (state.showApplyDialog){
        JobsApplyDialog(
            listOfJobs = state.videoListResponse.dataColl[index].academicList.map { academicList ->
                Triple(academicList.listName , academicList.jobList.map { availableJobs ->
                    availableJobs.jobName
                }, academicList.levels.map { availableLevels ->
                    availableLevels.levelName
                }
                )
            },
            onEvent = onEvent
        ){
            onEvent(DashboardEvent.ApplyNow(state.videoListResponse.dataColl[index]))
            onEvent(DashboardEvent.ShowApplyDialog(false))
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().animateContentSize(),
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
            Text(text = state.videoListResponse.dataColl[index].title)
            DropdownJobNepal(
                listOf(
                    Triple("Full Guid" , Icons.Default.YoutubeSearchedFor,true),
                    Triple("Apply Now" , Icons.Default.FastForward, !isApplied),
                    Triple("Apply Later", Icons.Default.Alarm, !isApplied)
                )
            ){
                when (it) {
                    "Apply Now" -> {
                        onEvent(DashboardEvent.ShowApplyDialog(true))
                    }
                    "Apply Later" -> {
                        onEvent(DashboardEvent.ApplyLetter(state.videoListResponse.dataColl[index]))
                    }
                    else -> {

                    }
                }


            }
        }
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            model = state.videoListResponse.dataColl[index].videoLink,
            contentDescription = "details",
            alignment = Alignment.Center
        )
        Column(
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
            if (isVisible){
                Text(
                    text = "240",
                    modifier = Modifier.padding(horizontal = 2.dp).fillMaxWidth(),
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
