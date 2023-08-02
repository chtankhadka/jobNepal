package com.chetan.jobnepal.screens.academic

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.AcademicDialog
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.dropdown.CascadeDropdownMenuJobNepal

data class MappedList(
    val id: String, val date: String, val name: String, val url: String
)

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
    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
            navigationIcon = {
                IconJobNepal(onClick = { navController.popBackStack()}, icon = Icons.Default.ArrowBack)
            },
            title = {
                Text(text = "Academic", style = MaterialTheme.typography.titleLarge)
            },
            actions = {
                CascadeDropdownMenuJobNepal(onLevelSelected = {
                    onEvent(AcademicEvent.LevelSelected(it))
                    onEvent(AcademicEvent.ShowDialog(true))
                    onEvent(AcademicEvent.ShowEdit(false))
                })
            })
    }, bottomBar = {}, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            state.infoMsg?.let {
                MessageDialog(
                    message = it,
                    onDismissRequest = {
                        if (onEvent != null && state.infoMsg.isCancellable == true){
                            onEvent(AcademicEvent.DismissInfoMsg)
                        }
                    },
                    onNegative = {},
                    onPositive = {}
                    )
            }

            if (state.academicListResponse.SEE.isNotEmpty()) {
                AcademicItem(
                    "SEE/SLC",
                    data = state.academicListResponse.SEE.map {
                        MappedList(
                            id = it.id, date = it.date, name = it.name, url = it.url
                        )
                    }.toMutableList(),
                    onClick = { task: String?, names: List<String> ->
                        if (task == "Delete") {
                            onEvent(
                                AcademicEvent.Delete("SEE", names)
                            )
                            onEvent(AcademicEvent.ShowEdit(false))
                        } else if (task == "Edit") {
                            onEvent(AcademicEvent.ShowEdit(true))
                        } else {
                            onEvent(
                                AcademicEvent.Delete("SEE", names)
                            )
                        }
                    },
                    showEdit = state.showEdit
                )
            }
            if (state.academicListResponse.IAC.isNotEmpty()) {
                AcademicItem(
                    "IAC",
                    showEdit = state.showEdit,
                    data = state.academicListResponse.IAC.map {
                        MappedList(
                            id = it.id, date = it.date, name = it.name, url = it.url
                        )
                    }.toMutableList(),
                    onClick = { task: String?, names: List<String> ->
                        if (task == "Delete") {
                            onEvent(
                                AcademicEvent.Delete("IAC", names)
                            )
                        } else if (task == "Edit") {
                            onEvent(AcademicEvent.ShowEdit(true))
                        } else {
                            onEvent(
                                AcademicEvent.Delete("IAC", names)
                            )
                        }
                    }
                )
            }
            if (state.academicListResponse.BSc_CSIT.isNotEmpty()) {
                AcademicItem("BSc.CSIT", data = state.academicListResponse.BSc_CSIT.map {
                    MappedList(id = it.id, date = it.date, name = it.name, url = it.url)
                }.toMutableList(), onClick = { task: String?, names: List<String> ->
                    if (task == "Delete") {
                        onEvent(
                            AcademicEvent.Delete("BSc_CSIT", names)
                        )
                    } else if (task == "Edit") {
                        onEvent(AcademicEvent.ShowEdit(true))
                    } else {
                        onEvent(
                            AcademicEvent.Delete("BSc_CSIT", names)
                        )
                    }
                }, showEdit = state.showEdit
                )
            }
            if (state.academicListResponse.CITIZENSHIP.isNotEmpty()){
                AcademicItem("Citizenship", data = state.academicListResponse.CITIZENSHIP.map {
                    MappedList(id = it.id, date = it.date, name = it.name, url = it.url)
                }.toMutableList(), onClick = { task: String?, names: List<String> ->
                    if (task == "Delete") {
                        onEvent(
                            AcademicEvent.Delete("Citizenship", names)
                        )
                    } else if (task == "Edit") {
                        onEvent(AcademicEvent.ShowEdit(true))
                    } else {
                        onEvent(
                            AcademicEvent.Delete("Citizenship", names)
                        )
                    }
                }, showEdit = state.showEdit
                )
            }

        }
    })

}


