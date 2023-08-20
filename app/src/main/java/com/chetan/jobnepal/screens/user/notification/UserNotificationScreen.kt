package com.chetan.jobnepal.screens.user.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse
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
        CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                items(state.userNotificationResponse) { notificationData ->
                    NotificationItem(notificationData) {
                        onEvent(UserNotificationEvent.readState(it))
                    }
                }

            })
        }
    })
}

@Composable
fun NotificationItem(
    notificationData: StoreNotificationRequestResponse, onClick: (String) -> Unit
) {
    Box(modifier = Modifier.padding(bottom = 5.dp)) {
        Card(
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable {
                    onClick(notificationData.time)
                }, elevation = CardDefaults.cardElevation(10.dp), shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Offer", style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = MyDate.differenceOfDates(
                        notificationData.time, System.currentTimeMillis().toString()
                    ), style = MaterialTheme.typography.bodySmall
                )

            }
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = notificationData.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Icon(
            modifier = Modifier.padding(start = 5.dp),
            imageVector = if (notificationData.readNotice) Icons.Default.NotificationsNone else Icons.Default.NotificationsActive,
            contentDescription = "notification"
        )
    }
}




