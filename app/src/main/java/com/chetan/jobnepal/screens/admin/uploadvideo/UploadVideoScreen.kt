package com.chetan.jobnepal.screens.admin.uploadvideo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.R
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.textfield.ReadonlyJobNepalTextField
import com.chetan.jobnepal.ui.component.textfield.TextFieldJobNepal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadVideoScreen(
    navController: NavHostController,
    state: UploadVideoState,
    onEvent: (event: UploadVideoEvent) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
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
                    text = "Upload Video", style = MaterialTheme.typography.titleLarge
                )
            })
    }, content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.infoMsg?.let {
                MessageDialog(
                    message = it,
                    onDismissRequest = {
                        onEvent(UploadVideoEvent.DismissInfoMsg)
                    },
                    onPositive = { }) {
                }
            }

            TextFieldJobNepal(
                value = state.id,
                onValueChange = {
                    onEvent(UploadVideoEvent.IdChange(it))
                },
                label = "Add video Id"
            )

            TextFieldJobNepal(
                value = state.url,
                onValueChange = {
                    onEvent(UploadVideoEvent.UrlChange(it))
                },
                label = "Add video link id",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            TextFieldJobNepal(
                value = state.title,
                onValueChange = {
                    onEvent(UploadVideoEvent.TitleChange(it))
                },
                label = "Title"
            )

            TextFieldJobNepal(
                value = state.description,
                onValueChange = {
                    onEvent(UploadVideoEvent.DescriptionChange(it))
                },
                label = "Add Description"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val provienceList = listOf(
                    "Province 1",
                    "Madhesh",
                    "Bagmati",
                    "Gandaki",
                    "Lumbini",
                    "Karnali",
                    "Sudurpaschim"
                )
                ReadonlyJobNepalTextField(
                    modifier = Modifier.weight(1f),
                    label = "Select Province",
                    value = state.editProvince,
                    onClick = {
                        expanded = !expanded
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }) {
                    provienceList.forEach {
                        DropdownMenuItem(text = {
                            Text(text = it)
                        }, onClick = {
                            onEvent(UploadVideoEvent.OnSelectProvince(it))
                            expanded = false
                        })
                    }
                }
            }
            Button(
                onClick = {
                    onEvent(UploadVideoEvent.UploadVideoUrl)
                }) {
                Text(text = stringResource(id = R.string.upload_data))

            }
            Spacer(modifier = Modifier.height(5.dp))

        }
    })

}