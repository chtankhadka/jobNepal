package com.chetan.jobnepal.screens.admin.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.utils.MyDate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminChatScreen(
    navController: NavHostController,
    state: AdminChatState,
    onEvent: (event: AdminChatEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text(
                text = "Video List",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Divider()
            LazyColumn(content = {
                items(state.videoIdList) { videoId ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                onEvent(AdminChatEvent.GetChatRequestList(videoId))
                                scope.launch {
                                    drawerState.close()
                                }
                            }, elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            textAlign = TextAlign.Center,
                            text = videoId,
                            style = MaterialTheme.typography.titleMedium,
                        )

                    }
                }
            })

        }

    }) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .shadow(4.dp, shape = RoundedCornerShape(20))
                            .clip(RoundedCornerShape(20))
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .clickable {
                                scope.launch {
                                    drawerState.open()
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Close"
                        )
                    }

                },
                title = {
                    Text(
                        text = "Messages", style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconJobNepal(
                        onClick = {
                            navController.popBackStack()
                        }, icon = Icons.Default.ArrowBack
                    )
                })
        }, content = {

            var showChatingDialog by remember {
                mutableStateOf(false)
            }

            if (showChatingDialog) {
                ChatingDialog(
                    onEvent = onEvent,
                    state = state
                ) {
                    showChatingDialog = false
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                state.infoMsg?.let {
                    MessageDialog(message = it, onDismissRequest = {
                        if (onEvent != null && state.infoMsg.isCancellable == true) {
                            onEvent(AdminChatEvent.DismissInfoMsg)
                        }
                    }, onPositive = { }, onNegative = {})
                }
                LazyColumn(content = {
                    items(state.chatUsersList) { data ->

                        Box() {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp)
                                    .padding(top = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = data.user)
                                    Card(
                                        modifier = Modifier.padding(5.dp),
                                        elevation = CardDefaults.cardElevation(10.dp)
                                    ) {
                                        IconButton(onClick = {
                                            onEvent(AdminChatEvent.GetChatHistory(userName = data.user))
                                            showChatingDialog = true
                                        }) {
                                            Text(text = "Chat")
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                            Icon(
                                modifier = Modifier.padding(start = 5.dp),
                                imageVector = if (data.newFromUser) Icons.Default.NotificationsNone else Icons.Default.NotificationsActive,
                                contentDescription = "notification"
                            )
                        }
                    }
                })

            }


        })
    }
}

@Composable
fun ChatingDialog(
    onEvent: (event: AdminChatEvent) -> Unit,
    state: AdminChatState,
    onDissmiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .padding( vertical = 20.dp)
                .background(color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
                .fillMaxSize(), // Fill maximum height
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
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .padding(top = 5.dp)
                                        .align(if (!data.self) Alignment.CenterEnd else Alignment.CenterStart),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors =
                                    if (!data.self) {
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
                                        }
                                        Text(
                                            text = data.msg,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                }
                                Text(
                                    modifier = Modifier.align(Alignment.TopCenter),
                                    text = MyDate.differenceOfDates(
                                        data.msgId,
                                        System.currentTimeMillis().toString()
                                    ),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                )
                            }

                        }

                    }

                )
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopEnd),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    IconButton(
                        onClick = {
                            onDissmiss()
                        }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = ""
                        )
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(5.dp), // Fill maximum width
                value = state.userMsg,
                placeholder = {
                    Text(text = "Write a comment...")
                },
                trailingIcon = {
                    if (state.userMsg.isNotBlank()) {
                        androidx.compose.material3.IconButton(onClick = {
                            onEvent(AdminChatEvent.SetChatHistory)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send Comment"
                            )
                        }
                    }
                },
                onValueChange = {
                    onEvent(AdminChatEvent.OnMsgChange(it))
                }
            )

        }

    }
}



