package com.chetan.jobnepal.screens.user.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.R
import com.chetan.jobnepal.ui.component.IconJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavHostController,
    state: NotificationState,
    onEvent: (event: NotificationEvent) -> Unit
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
            Column(modifier = Modifier.padding(it)) {

            }
        })
}

