package com.chetan.jobnepal.screens.user.dashboard

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.utils.VibratingIcon
import com.chetan.jobnepal.utils.ads.BannerAd
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        onEvent(DashboardEvent.OnRefresh)
        refreshing = false
    }
    if (state.showApplyDialog){
        Dialog(onDismissRequest = {
            onEvent(DashboardEvent.ShowApplyDialog(false,""))
        }) {
            Card(elevation = CardDefaults.cardElevation(10.dp)) {
                Column(
                    horizontalAlignment =Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Enter Details",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(vertical = 10.dp),
                        textAlign =  TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                        )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        minLines = 3,
                        maxLines = 5,
                        value = state.onChangeJobDescription,
                        onValueChange = {
                            onEvent(DashboardEvent.OnAppliedJobDescriptionChange(it))
                        })
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onEvent(DashboardEvent.ApplyNow)
                            }) {
                            Text(text = "Apply Now")
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onEvent(DashboardEvent.ShowApplyDialog(false,""))
                        }) {
                            Text(text = "Cancel")
                        }
                    }


                }

            }
        }
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    var cardVisible by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = cardVisible, block = {
        if (cardVisible) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                cardVisible = false
            }, 7000)
        }
    })

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ModalDrawerSheetPage(navController, state, onClick, onEvent)
            }

        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
                .background(Color.Magenta),
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
                                        .background(MaterialTheme.colorScheme.primaryContainer)
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
                                        contentDescription = "Menu"
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
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    VibratingIcon(Icons.Default.NotificationsActive, isVibrating = state.isVibrating) {
                                        navController.navigate(Destination.Screen.UserNotification.route)
                                    }
                                }
                            },

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
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.spacedBy(5.dp)
//                            ) {
//                                ExposedDropdownJobNepal(
//                                    modifier = Modifier.weight(0.5f),
//                                    list = arrayOf(
//                                        "Province",
//                                        "Province 1",
//                                        "Province 2",
//                                        "Province 3",
//                                        "Province 4",
//                                        "Province 5"
//                                    ),
//                                    onClick = {
//                                        onEvent(DashboardEvent.OnProvinceFilter(it))
//                                    }
//                                )
//                                ExposedDropdownJobNepal(
//                                    modifier = Modifier.weight(0.5f),
//                                    list = arrayOf(
//                                        "Field",
//                                        "IT",
//                                        "Health",
//                                        "Bank"
//                                    ),
//                                    onClick = {
//                                        onEvent(DashboardEvent.OnFieldFilter(it))
//                                    }
//
//                                )
//                                ExposedDropdownJobNepal(
//                                    modifier = Modifier.weight(0.5f),
//                                    list = arrayOf("All", "Technical", "Non Technical"),
//                                    onClick = {
////                                    onEvent(DashboardEvent.OnProvinceFilter(it))
//                                    }
//                                )
//
//                            }
//                            Spacer(modifier = Modifier.height(5.dp))
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                items(state.videoListResponse) { videoList ->
                                    DashboardItem(
                                        state = state,
                                        data = videoList,
                                        isApplied = state.appliedIdsList.contains(videoList.id),
                                        onEvent = onEvent,
                                        navController = navController

                                    )

                                }

                            }

                        }

                    }

                )
            }
//            var query by remember {
//                mutableStateOf("")
//            }
//            CustomSearchBar(query = query, onQueryChange = {
//                query = it
//            },
//                modifier = Modifier.padding(top = 7.dp),
//                placeholder = "Search Here"
//            )
            DockedSearchBar(
                colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(0.75f)
                    .zIndex(1f),
                query = state.searchText,
                onQueryChange = {
                    onEvent(DashboardEvent.OnQueryChangeOnSearch(it))
                },
                onSearch = {
                    onEvent(DashboardEvent.OnQuerySearchOnSearch(it))
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                BannerAd(
                    bId = "ca-app-pub-1412843616436423/5772386299",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = cardVisible, enter = fadeIn(), exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .padding(horizontal = 7.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Get ${state.updatedNotice.discountPercentage}% Discount on your first form!!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = MaterialTheme.colorScheme.error
                                )
                            ) {
                                append("Rs ${state.updatedNotice.totalCost}")
                            }
                            withStyle(SpanStyle()){
                                append(" Rs ${state.updatedNotice.discountAmt}")
                            }
                        }

                    )
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing, state = refreshState, modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )
        }
    }

}