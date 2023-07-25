package com.chetan.jobnepal.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.ui.component.DashboardItem
import com.chetan.jobnepal.ui.component.dropdown.ExposedDropdownJobNepal
import com.chetan.jobnepal.utils.VibratingIcon
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    onClick: (String) -> Unit,
    state: DashboardState,
    onEvent: (event: DashboardEvent) -> Unit
) {

    val scope = rememberCoroutineScope()

    var searchText by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    val items = remember {
        mutableStateListOf(
            "Butwal Loksewa",
            "Lumbini Pardesh",
            "Karnali Pardesh"
        )
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ModalDrawerSheetPage(navController,state,onClick)
            }

        }) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        navigationIcon = {
                            Box(
                                modifier = Modifier
                                    .shadow(4.dp, shape = RoundedCornerShape(20))
                                    .clip(RoundedCornerShape(20))
                                    .background(MaterialTheme.colorScheme.onPrimary)
                                    .clickable {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Close"
                                )
                            }
                        },
                        title = {


                        },
                        actions = {
                            Box(
                                modifier = Modifier
                                    .shadow(4.dp, shape = RoundedCornerShape(20))
                                    .clip(RoundedCornerShape(20))
                                    .background(MaterialTheme.colorScheme.onPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                VibratingIcon(Icons.Default.NotificationsActive){
                                    navController.navigate(Destination.Screen.UploadVideoScreen.route)
                                }
                            }
                        }
                    )

                },
                bottomBar = {

                },

                //Dashboard Content
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(horizontal = 7.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            ExposedDropdownJobNepal(
                                modifier = Modifier.weight(0.5f),
                                list = arrayOf(
                                    "Province",
                                    "Province 1",
                                    "Province 2",
                                    "Province 3"
                                )
                            )
                            ExposedDropdownJobNepal(
                                modifier = Modifier.weight(0.5f),
                                list = arrayOf(
                                    "Field",
                                    "IT",
                                    "Health",
                                    "Bank"
                                )

                            )
                            ExposedDropdownJobNepal(
                                modifier = Modifier.weight(0.5f),
                                list = arrayOf("All", "Technical", "Non Technical"),
                            )

                        }
                        val list = listOf(
                            "Nepal" to "https://img.freepik.com/free-photo/hand-writing-paper-with-pen_1232-1344.jpg?w=1380&t=st=1686458933~exp=1686459533~hmac=36ba0df24c3cf4a8a190ed558c34b8f76e60647edf2d0cde383a42bc5982782c",
                            "Gopal" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvcAvHvN_TYQn2SFov3FKgnU_Ygdy9OwrzJQ&usqp=CAU",
                            "Sopal" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbfcnFAfUXHQOD3jecWzEaSfV8iqjfQyf4Bg&usqp=CAU",
                            "Nopal" to "https://www.wikihow.com/images/thumb/f/f2/Prepare-for-an-Exam-Step-2-Version-2.jpg/v4-460px-Prepare-for-an-Exam-Step-2-Version-2.jpg.webp"
                        )
                        LazyColumn(
                            modifier = Modifier,
                            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(list.size) {position ->
                                DashboardItem(list[position])
                            }
                        }

                    }

                }

            )
            DockedSearchBar(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(top = 4.dp)
                    .zIndex(1f),
                query = searchText,
                onQueryChange = {
                    searchText = it
                },
                onSearch = {
                    items.add(it)
                    active = false
                    searchText = ""
                },
                active = active,
                onActiveChange = {
                    active = true
                },
                placeholder = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                active = false
                            },
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                searchText = ""
                            }
                        )
                    } else {
                        if (active) {
                            Icon(imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    active = false
                                }
                            )
                        }
                    }

                },
                content = {
                    items.forEach {
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(0.7f)
                            ) {
                                Icon(
                                    modifier = Modifier.padding(end = 5.dp),
                                    imageVector = Icons.Default.History,
                                    contentDescription = ""
                                )
                                Text(text = it)
                            }
                            Icon(
                                modifier = Modifier
                                    .size(15.dp)
                                    .clickable {

                                    },
                                imageVector = Icons.Default.Close,
                                contentDescription = ""
                            )


                        }
                    }
                }
            )
        }
    }


}