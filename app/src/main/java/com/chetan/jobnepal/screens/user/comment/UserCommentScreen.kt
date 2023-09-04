package com.chetan.jobnepal.screens.user.comment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.utils.MyDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCommentScreen(
    navController: NavHostController,
    state: UserCommentState,
    onEvent: (event: UserCommentEvent) -> Unit,
    vid: String,
) {
    onEvent(UserCommentEvent.GetUsersComment(vid))
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)),
            navigationIcon = {

                IconJobNepal(
                    onClick = {
                        navController.popBackStack()
                    }, icon = Icons.Default.ArrowBack
                )
            },
            title = {
                Text(
                    text = "Notification", style = MaterialTheme.typography.titleLarge
                )
            })
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp)
                .fillMaxHeight(), // Fill maximum height
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f) // Occupy available space
                    .fillMaxWidth() // Fill maximum width
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                        reverseLayout = true,
                    content = {
                        items(state.userCommentList) { data ->
                                Card(
                                    modifier = Modifier.padding(10.dp),
                                    elevation = CardDefaults.cardElevation(10.dp)) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                            Text(text = data.userName, style = MaterialTheme.typography.titleSmall)
                                            Text(text = MyDate.differenceOfDates(
                                                data.commentId,System.currentTimeMillis().toString()),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = MaterialTheme.colorScheme.outline))
                                        }
                                        Text(text = data.comment, style = MaterialTheme.typography.bodyMedium)
                                    }

                                }
                        }
                    }
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(), // Fill maximum width
                value = state.userComment,
                placeholder = {
                    Text(text = "Write a comment...")
                },
                trailingIcon = {
                    if (state.userComment.isNotBlank()){
                        IconButton(onClick = { onEvent(UserCommentEvent.SetUserComment) }) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Send Comment")
                        }
                    }

                },
                onValueChange = {
                    onEvent(UserCommentEvent.OnCommentChange(it))
                }
            )
        }

    })
}