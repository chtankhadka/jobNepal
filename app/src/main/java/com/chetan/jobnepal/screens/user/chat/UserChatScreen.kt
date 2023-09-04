package com.chetan.jobnepal.screens.user.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.utils.MyDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserChatScreen(
    state: UserChatState,
    onEvent: (event: UserChatEvent) -> Unit,
    vid: String,
    navController: NavHostController,
) {
    onEvent(UserChatEvent.GetChatHistory(vid))
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
                    text = "Message", style = MaterialTheme.typography.titleLarge
                )
            })
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
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
                        items(state.userChatHistory) { data ->
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(modifier = Modifier
                                .fillMaxWidth()) {
                                Card(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .align(if (data.self) Alignment.CenterEnd else Alignment.CenterStart),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors =
                                    if (data.self) {
                                        CardDefaults.cardColors(
                                            MaterialTheme.colorScheme.primaryContainer
                                        )
                                    } else {
                                        CardDefaults.cardColors()
                                    }

                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                            Text(
                                                text = data.userName,
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                            Text(
                                                text = MyDate.differenceOfDates(
                                                    data.msgId,
                                                    System.currentTimeMillis().toString()
                                                ),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = MaterialTheme.colorScheme.outline
                                                )
                                            )
                                        }
                                        Text(
                                            text = data.msg,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                }
                            }

                        }

                    }

                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(), // Fill maximum width
                value = state.userMsg,
                placeholder = {
                    Text(text = "Write a comment...")
                },
                trailingIcon = {
                    if (state.userMsg.isNotBlank()) {
                        IconButton(onClick = { onEvent(UserChatEvent.SetChatHistory) }) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send Comment"
                            )
                        }
                    }
                },
                onValueChange = {
                    onEvent(UserChatEvent.OnMsgChange(it))
                }
            )
        }

    })
}