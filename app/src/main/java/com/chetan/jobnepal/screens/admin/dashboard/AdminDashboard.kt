package com.chetan.jobnepal.screens.admin.dashboard

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.utils.VibratingIcon
import kotlinx.coroutines.launch

enum class MultiFloatingState {
    Expanded,
    Collapsed
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AdminDashboard(
    navController: NavHostController,
    state: AdminDashboardState,
    onEvent: (event: AdminDashboardEvent) -> Unit,
    onClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ModalDrawerSheetPage(
                    state = state,
                    onEvent = onEvent,
                    onClick = {
                        when (it){

                            MenuItem.Documents -> {

                            }
                            MenuItem.Offer -> {
                                navController.navigate(Destination.Screen.AdminBottomSheetNotice.route)
                            }
                            MenuItem.Notification -> {
                                navController.navigate(Destination.Screen.AdminSendNotification.route)
                            }
                            MenuItem.Payments -> {
                                navController.navigate(Destination.Screen.AdminPayment.route)
                            }

                            MenuItem.FormRequest -> {
                                navController.navigate(Destination.Screen.UserFormRequestScreen.route)
                            }
                        }
                    }
                )
            }

        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
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
                                VibratingIcon(Icons.Default.NotificationsActive) {
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


                    }

                },
                floatingActionButton = {
                    MultiFloatingButton(
                        multiFloatingState = multiFloatingState,
                        onMultiFabStateChange = {
                            multiFloatingState = it
                        },
                        onClick = {
                            onClick(it)
                        }
                    )
                }

            )
        }
    }
}


@Composable
fun MultiFloatingButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    onClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")
    val rotateState = remember {
        Animatable(initialValue = 0f)
    }
    val targetRotation = if (multiFloatingState == MultiFloatingState.Expanded) 0f else -315f
    Column(
        modifier = Modifier.background(Color.Transparent),
        verticalArrangement = Arrangement.spacedBy(5.dp)){
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = tween(durationMillis = 1000),

                        )
                    .background(Color.Transparent)
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (transition.currentState == MultiFloatingState.Expanded) {
                    SmallFloatingActionButton(
                        onClick = {
                            onClick("logout")
                        }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = "logout")
                    }
                    LargeFloatingActionButton(onClick = {
                        onClick("addVideo")
                    }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Default.AddAPhoto, contentDescription ="" )
                    }
                }

            }
        FloatingActionButton(
            onClick = {
                scope.launch {
                    rotateState.animateTo(
                        targetValue = targetRotation,
                        animationSpec = tween(durationMillis = 1000)
                    )
                }
                onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.Expanded) {
                        MultiFloatingState.Collapsed
                    } else {
                        MultiFloatingState.Expanded
                    }
                )
            },

            ) {
            Icon(
                modifier = Modifier.rotate(rotateState.value),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}



