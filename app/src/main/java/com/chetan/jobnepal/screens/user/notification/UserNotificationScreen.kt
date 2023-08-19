package com.chetan.jobnepal.screens.user.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.utils.MyDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNotificationScreen(
    navController: NavHostController,
    state: UserNotificationState,
    onEvent: (event: UserNotificationEvent) -> Unit
) {

    val ctx = LocalContext.current
    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), navigationIcon = {
            IconJobNepal(
                onClick = {
                    navController.popBackStack()
                }, icon = Icons.Default.ArrowBack
            )
        }, title = {
            Text(
                text = "Notification",
                style = MaterialTheme.typography.titleLarge
            )
        })
    },
        content = {
            Column(modifier = Modifier
                .padding(it)) {
                LazyColumn(content = {
                    items(state.userNotificationResponse){
                        Card() {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationAdd,
                                    contentDescription = "notification"
                                )
                                Text(text = MyDate.differenceOfDates(it.time, System.currentTimeMillis().toString()))

                            }
                            Text(text = it.body)
                        }
                    }

                })
            }
        })
}

