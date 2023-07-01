package com.chetan.jobnepal.screens.academic

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.AcademicDialog
import com.chetan.jobnepal.ui.component.dropdown.CascadeDropdownMenuJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Academic(navController: NavHostController) {
    val list = listOf(
        "Nepal" to "https://img.freepik.com/free-photo/hand-writing-paper-with-pen_1232-1344.jpg?w=1380&t=st=1686458933~exp=1686459533~hmac=36ba0df24c3cf4a8a190ed558c34b8f76e60647edf2d0cde383a42bc5982782c",
        "Gopal" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvcAvHvN_TYQn2SFov3FKgnU_Ygdy9OwrzJQ&usqp=CAU",
        "Sopal" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbfcnFAfUXHQOD3jecWzEaSfV8iqjfQyf4Bg&usqp=CAU",
        "Nopal" to "https://www.wikihow.com/images/thumb/f/f2/Prepare-for-an-Exam-Step-2-Version-2.jpg/v4-460px-Prepare-for-an-Exam-Step-2-Version-2.jpg.webp"
    )
    var showAcademicDialog by remember {
        mutableStateOf(false)
    }

    if (showAcademicDialog) {
        AcademicDialog(
            list
        ) {
            showAcademicDialog = false
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                navigationIcon = {
                    IconJobNepal(onClick = { }, icon = Icons.Default.ArrowBack)
                },
                title = {
                    Text(text = "Academic", style = MaterialTheme.typography.titleLarge)
                },
                actions = {
                    CascadeDropdownMenuJobNepal(
                        onLevelSelected = {
                            showAcademicDialog = true
                        }
                    )
                }
            )
        },
        bottomBar = {},
        content = {

            LazyColumn(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(list.size) {
                    AcademicItem(list)
                }


            }
        }
    )
}
