package com.chetan.jobnepal.screens.user.dashboard


import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal
import com.chetan.jobnepal.utils.youtubePlayer.WebContent

@Composable
fun DashboardItem(
    data: UploadNewVideoLink,
    isApplied: Boolean,
    onEvent: (event: DashboardEvent) -> Unit,
    state: DashboardState,
    navController: NavHostController
) {
    val ctx = LocalContext.current
    var isVisible by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
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
            Text(text = data.title, style = MaterialTheme.typography.titleMedium)
            DropdownJobNepal(
                listOf(
                    Triple("Full Guid", Icons.Default.YoutubeSearchedFor, true),
                    Triple("Apply Now", Icons.Default.FastForward, !isApplied)
                )
            ) {
                when (it) {
                    "Apply Now" -> {
                        onEvent(DashboardEvent.ShowApplyDialog(true, data.id))
                    }

                    else -> {
                        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.videoUrl))
                        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(ctx, appIntent, null)

                    }
                }


            }
        }
        WebContent(videoId = data.videoId, modifier = Modifier.height(315.dp))
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
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.clickable {
                                navController.navigate(Destination.Screen.UserComment.route.replace(
                                    "{vid}",data.id
                                ))
                            },
                            imageVector = Icons.Default.Comment,
                            contentDescription = "",
                        )

                    }
                }
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
                    text = data.description,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
