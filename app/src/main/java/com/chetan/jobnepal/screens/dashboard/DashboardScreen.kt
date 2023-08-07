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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.dropdown.ExposedDropdownJobNepal
import com.chetan.jobnepal.utils.VibratingIcon
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
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

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        onEvent(DashboardEvent.OnRefresh)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ModalDrawerSheetPage(navController, state, onClick, onEvent)
            }

        }) {
        Box(
            modifier = Modifier.fillMaxSize().pullRefresh(refreshState).background(Color.Magenta),
            contentAlignment = Alignment.TopCenter
        ) {
            if (!refreshing) {
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
                                    VibratingIcon(Icons.Default.NotificationsActive) {
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
                            state.infoMsg?.let {
                                MessageDialog(
                                    message = it,
                                    onDismissRequest = {
                                        if (onEvent != null && state.infoMsg.isCancellable == true) {
                                            onEvent(DashboardEvent.DismissInfoMsg)
                                        }
                                    },
                                    onPositive = { },
                                    onNegative = {})
                            }
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
                                    ),
                                    onClick = {
                                        onEvent(DashboardEvent.OnProvinceFilter(it))
                                    }
                                )
                                ExposedDropdownJobNepal(
                                    modifier = Modifier.weight(0.5f),
                                    list = arrayOf(
                                        "Field",
                                        "IT",
                                        "Health",
                                        "Bank"
                                    ),
                                    onClick = {
                                        onEvent(DashboardEvent.OnFieldFilter(it))
                                    }

                                )
                                ExposedDropdownJobNepal(
                                    modifier = Modifier.weight(0.5f),
                                    list = arrayOf("All", "Technical", "Non Technical"),
                                    onClick = {
//                                    onEvent(DashboardEvent.OnProvinceFilter(it))
                                    }
                                )

                            }
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 5.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {

                                items(state.videoListResponse) { videoList ->
                                    DashboardItem(
                                        state = state,
                                        list = videoList,
                                        isApplied = state.appliedIdsList.contains(videoList.id),
                                        onEvent = onEvent
                                    )

                                }

                            }

                        }

                    }

                )
            }
            DockedSearchBar(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(top = 4.dp)
                    .zIndex(1f),
                query = state.searchText,
                onQueryChange = {
                    onEvent(DashboardEvent.OnQueryChangeOnSearch(it))
                },
                onSearch = {
                    onEvent(DashboardEvent.OnQuerySearchOnSearch(it))
                    items.add(it)
                    active = false
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
                    LazyColumn() {
                        items(state.searchListResponse) {
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
                                    Text(text = it.searchValue)
                                }
                                Icon(
                                    modifier = Modifier
                                        .size(15.dp)
                                        .clickable {
                                            onEvent(DashboardEvent.OnQuerySearchDelete(it.searchTime))
                                        },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )


                            }
                        }
                    }

                },
            )
            PullRefreshIndicator(
                refreshing = refreshing, state = refreshState, modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )
        }
    }


}