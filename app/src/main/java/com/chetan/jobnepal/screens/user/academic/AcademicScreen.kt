package com.chetan.jobnepal.screens.user.academic

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.AcademicDialog
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.dropdown.CascadeDropdownMenuJobNepal
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AcademicScreen(
    navController: NavHostController, state: AcademicState, onEvent: (event: AcademicEvent) -> Unit
) {
    if (state.showDialog) {
        AcademicDialog(state, onDismissRequest = {
            onEvent(AcademicEvent.ShowDialog(false))
        }) {
            onEvent(AcademicEvent.UploadAttachement(it))
        }
    }
    var alertDialog by remember {
        mutableStateOf(false)
    }
    if (alertDialog){
        Dialog(
            onDismissRequest = { alertDialog = false }) {
            Card(elevation = CardDefaults.cardElevation(10.dp)) {
                Column(modifier = Modifier.padding(10.dp),
                    horizontalAlignment =Alignment.CenterHorizontally){
                    Text(text = "Are you sure \n want to delete ?")
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(onClick = {
                            onEvent(AcademicEvent.Delete)
                            alertDialog = false
                        }) {
                            Text(text = "Yes")

                        }
                        Button(onClick = {
                            alertDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                }

            }
        }
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
            navigationIcon = {
                IconJobNepal(
                    onClick = { navController.popBackStack() }, icon = Icons.Default.ArrowBack
                )
            },
            title = {
                Text(text = "Documents", style = MaterialTheme.typography.titleLarge)
            },
            actions = {
                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                ) {
                    IconJobNepal(
                        onClick = {
                            expanded = true
                        }, icon = Icons.Default.AddBox
                    )
                    CascadeDropdownMenuJobNepal(expanded = expanded, onDismiss = {
                        expanded = false
                    }, onLevelSelected = {
                        onEvent(AcademicEvent.LevelSelected(it))
                        onEvent(AcademicEvent.ShowDialog(true))
                        onEvent(AcademicEvent.ShowEdit(false))
                    })
                }
            })
    }, bottomBar = {}, content = {
        Column(
            modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            state.infoMsg?.let {
                MessageDialog(message = it, onDismissRequest = {
                    if (onEvent != null && state.infoMsg.isCancellable == true) {
                        onEvent(AcademicEvent.DismissInfoMsg)
                    }
                }, onNegative = {}, onPositive = {})
            }

            var showAcademicDropDownList by remember {
                mutableStateOf(false)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box() {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(end = 50.dp)
                            .clickable {
                                showAcademicDropDownList = true
                            }, elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                            text = state.selectedLevel,
                            style = MaterialTheme.typography.titleMedium
                        )
                        CascadeDropdownMenuJobNepal(expanded = showAcademicDropDownList,
                            onDismiss = {
                                showAcademicDropDownList = false
                            },
                            onLevelSelected = {
                                onEvent(AcademicEvent.GetAcademicEvent(it))
                                showAcademicDropDownList = false
                            })
                    }
                }
                if (state.selectedLevel.isNotEmpty()){
                    Card(elevation = CardDefaults.cardElevation(10.dp)) {

                        DropdownJobNepal(
                            list = listOf(
                                Triple("Edit", Icons.Filled.Edit, true),
                                Triple("Delete", Icons.Default.Delete, true)
                            )
                        ) {
                            when(it){
                                "Edit" -> {
                                    onEvent(AcademicEvent.ShowEdit(true))
                                }
                                else ->{
                                    alertDialog = true
                                }
                            }
                        }
                        Divider()

                    }
                }


            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                items(state.academicListResponse) { data ->
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(5.dp))
                    ) {
                        AsyncImage(
                            model = data.url,
                            contentDescription = "",
                            contentScale = ContentScale.Inside
                        )
                        if (state.showEdit) {
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.TopEnd),
                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {
                                IconButton(onClick = {
                                    onEvent(AcademicEvent.DeleteSelectedItem(data))
                                }) {
                                    Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                                }
                            }
                        }
                    }
                }
            }

        }
    })

}



