package com.chetan.jobnepal.screens.admin.uploadvideo

import JobsSelectionDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun UploadVideoScreen(
    navController: NavHostController,
    state: UploadVideoState,
    onEvent: (event: UploadVideoEvent) -> Unit
) {
    if (state.showJobDialog){
        JobsSelectionDialog(onEvent) {
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = state.id,
            onValueChange = {
                onEvent(UploadVideoEvent.IdChange(it))
            },
            label = {
                Text(text = "Add Id")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = state.url ,
            onValueChange = {
                onEvent(UploadVideoEvent.UrlChange(it))
            },
            label = {
                Text(text = "Add video url")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = state.title,
            onValueChange = {
               onEvent(UploadVideoEvent.TitleChange(it))
            },
            label = {
                Text(text = "Title")
            },
        )
        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = state.description ,
            onValueChange = {
                onEvent(UploadVideoEvent.DescriptionChange(it))
            },
            label = {
                Text(text = "Add Description")
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick = {
            onEvent(UploadVideoEvent.SetCheckedList(true))
        }) {
            Text(text = "Job For")
        }
        Button(
            onClick = {
                onEvent(UploadVideoEvent.UploadVideoUrl)
            }) {
            Text(text = "Upload Data")

        }
        Spacer(modifier = Modifier.height(5.dp))

        var clicked by remember {
         mutableStateOf(false)
        }
        Button(
            onClick = {
                onEvent(UploadVideoEvent.Reset)
            }) {
            Text(text = "Reset")

        }
        Button(
            onClick = {
                onEvent(UploadVideoEvent.DownloadVideoUrl)
                clicked = true
            }) {
            Text(text = "get Data")

        }
        LazyColumn(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Blue),
            contentPadding = PaddingValues(2.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(state.videoList.dataColl){
                Text(text =it.id)
                Text(text = it.title)
                Text(text = it.videoLink)
                Text(text = it.description)
            }

        }

    }
}