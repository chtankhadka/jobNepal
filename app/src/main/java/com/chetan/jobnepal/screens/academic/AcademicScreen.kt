package com.chetan.jobnepal.screens.academic

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.AcademicDialog
import com.chetan.jobnepal.ui.component.dropdown.CascadeDropdownMenuJobNepal
import timber.log.Timber

data class MappedList(
    val id: String, val date: String, val name: String, val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AcademicScreen(
    navController: NavHostController, state: AcademicState, onEvent: (event: AcademicEvent) -> Unit
) {
    val list = listOf(
        "Gopal" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvcAvHvN_TYQn2SFov3FKgnU_Ygdy9OwrzJQ&usqp=CAU",
        "Sopal" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbfcnFAfUXHQOD3jecWzEaSfV8iqjfQyf4Bg&usqp=CAU",
        "Nopal" to "https://www.wikihow.com/images/thumb/f/f2/Prepare-for-an-Exam-Step-2-Version-2.jpg/v4-460px-Prepare-for-an-Exam-Step-2-Version-2.jpg.webp"
    )
    if (state.showDialog) {
        AcademicDialog(state, list = list, onDismissRequest = {
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
            modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (!state.academicListResponse.SEE.isEmpty()) {
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
            if (!state.academicListResponse.IAC.isEmpty()) {
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
            Text(text = state.downloadAttachementUrl.toString())
            if (!state.academicListResponse.BSc_CSIT.isEmpty()) {
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

        }
    })

}

