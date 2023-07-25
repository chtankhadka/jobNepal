package com.chetan.jobnepal.screens.account

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chetan.jobnepal.data.enums.Gender
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.animation.YouCannotClickMe
import com.chetan.jobnepal.ui.component.textfield.EnumTextFieldJobNepal
import com.chetan.jobnepal.ui.component.textfield.TextFieldJobNepal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    state: ProfileState,
    onEvent: (event: ProfileEvent) -> Unit
) {
    val ctx = LocalContext.current

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                navigationIcon = {
                    IconJobNepal(
                        onClick = {
                                  navController.popBackStack()
                        },
                        icon = Icons.Default.ArrowBack
                    )
                },
                title = {
                    Text(text = "Profile", style = MaterialTheme.typography.titleLarge)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                    AsyncImage(
                        model = selectedImageUri?:state.imageUrl,
                        contentDescription = ""
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Column(
                        modifier = Modifier.padding(7.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextFieldJobNepal(
                                modifier = Modifier.weight(0.85f),
                                label = "First Name",
                                value = state.editFirstName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnFirstNameChange(it))
                                },
                                required = true,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            FloatingActionButton(
                                modifier = Modifier.weight(0.15f),
                                onClick = {
                                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Camera,
                                    contentDescription = ""
                                )
                            }

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextFieldJobNepal(
                                modifier = Modifier.weight(1f),
                                label = "Middle Name",
                                value = state.editMiddleName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnMiddleNameChange(it))
                                })
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(
                                modifier = Modifier.weight(1f),
                                label = "Last Name",
                                value = state.editLastName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnLastNameChange(it))
                                }, required = true
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            EnumTextFieldJobNepal(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                options = Gender.values().toList(),
                                asString = { this?.resId?.let { ctx.getString(it) } ?: "" },
                                label = "Gender",
                                value = state.editGender,
                                onValueChange = { onEvent(ProfileEvent.OnGenderChange(it)) },
                                required = true
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(
                                label = "Date of birth",
                                value = state.editLastName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnLastNameChange(it))
                                }, required = true,
                                singleLine = true
                            )
                        }
                        TextFieldJobNepal(
                            label = "Email",
                            value = state.editEmail,
                            onValueChange = {
                                onEvent(ProfileEvent.OnEmailChange(it))
                            }, required = true
                        )


                    }


                }
                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .animateContentSize(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Column(
                        modifier = Modifier.padding(7.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        var isVisible by remember {
                            mutableStateOf(false)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Other details")
                            Icon(
                                imageVector = if (!state.isOtherVisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    onEvent(ProfileEvent.OnOtherDetailsClicked(!state.isOtherVisible))
                                }
                            )
                        }
                        Divider(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
                        if (state.isOtherVisible) {
                            TextFieldJobNepal(
                                label = "Father First Name",
                                value = state.editFatherFirstName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnFatherFirstNameChange(it))
                                })
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                TextFieldJobNepal(
                                    modifier = Modifier.weight(1f),
                                    label = "Father Middle Name",
                                    value = state.editFatherMiddleName,
                                    onValueChange = {
                                        onEvent(ProfileEvent.OnFatherMiddleNameChange(it))
                                    })
                                Spacer(modifier = Modifier.width(5.dp))
                                TextFieldJobNepal(
                                    modifier = Modifier.weight(1f),
                                    label = "Father Last Name",
                                    value = state.editFatherLastNam,
                                    onValueChange = {
                                        onEvent(ProfileEvent.OnFatherLastNameChange(it))
                                    }, required = true
                                )
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                YouCannotClickMe(
                    enable = !state.areAllDataFilled(),
                    size = 200f,
                    buttonWidth = 100,
                    buttonHeight = 50,
                    text = "Upload",
                    boxColor = Color.Transparent,
                    buttonColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    onClick = {
                        Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show()
                        selectedImageUri?.let { it1 -> ProfileEvent.Upload(it1) }
                            ?.let { it2 -> onEvent(it2) }
                    })


            }

        }
    )
}