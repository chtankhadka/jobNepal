package com.chetan.jobnepal.screens.user.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCommentScreen(
    navController: NavHostController,
    state: UserCommentState,
    onEvent: (event: UserCommentEvent) -> Unit
) {
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
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            LazyColumn(
                modifier = Modifier,
                content = {
                    items(state.userCommentList) {data ->
                        Text(text = data.comment )
                    }
                })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.userComment,
                placeholder = {
                    Text(text = "Write a comment...")
                },
                trailingIcon = {
                    IconButton(onClick = { onEvent(UserCommentEvent.SetUserComment) }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send Comment" )
                    }

                },
                onValueChange = {
                    onEvent(UserCommentEvent.OnCommentChange(it))
                })


        }
    })
}