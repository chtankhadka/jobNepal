package com.chetan.jobnepal.screens.admin.formrequest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chetan.jobnepal.data.models.adminpayment.PaidPaymentDetails
import com.chetan.jobnepal.data.models.formrequest.FormRequestJobDetails
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun FormRequestScreen(
    navController: NavHostController,
    state: FormRequestState,
    onEvent: (event: FormRequestEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val pagerList = listOf("Request", "Paid")
    val pagerState = rememberPagerState(initialPage = 0) { 3 }

    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.6f)
        ) {
            Text(
                text = "Video List",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Divider()
            state.userPaymentVideoIdList.forEachIndexed { index, videoId ->
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            onEvent(FormRequestEvent.GetFormRequestsOfId(videoId))
                            scope.launch {
                                drawerState.close()
                            }
                        }, elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        textAlign = TextAlign.Center,
                        text = videoId,
                        style = MaterialTheme.typography.titleMedium,
                    )

                }
            }
        }

    }) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
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
                            }, contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Close"
                        )
                    }

                },
                title = {
                    Text(
                        text = "Form Request", style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconJobNepal(
                        onClick = {
                            navController.popBackStack()
                        }, icon = Icons.Default.ArrowBack
                    )
                })
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                state.infoMsg?.let {
                    MessageDialog(message = it, onDismissRequest = {
                        if (onEvent != null && state.infoMsg.isCancellable == true) {
                            onEvent(FormRequestEvent.DismissInfoMsg)
                        }
                    }, onPositive = { }, onNegative = {})
                }
                TabRow(selectedTabIndex = pagerState.currentPage,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    divider = {}) {
                    pagerList.forEachIndexed { index, page ->
                        Tab(selected = pagerState.currentPage == index, onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }, text = {
                            Text(
                                text = page,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = if (pagerState.currentPage == index) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodySmall
                            )
                        })
                    }
                }
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                    when (page) {
                        0 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                state.paymentReceiptList.filter { paidPaymentDetails ->
                                    !paidPaymentDetails.approved
                                }.forEach { filterPaidPaymentDetails ->
                                    FormRequestItem(
                                        filterPaidPaymentDetails,
                                        onEvent,
                                        state.userAppliedFormDetails
                                    )
                                }

                            }
                        }

                        1 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                state.paymentReceiptList.filter { paidPaymentDetails ->
                                    paidPaymentDetails.approved
                                }.forEach { filterPaidPaymentDetails ->
                                    FormRequestItem(
                                        filterPaidPaymentDetails,
                                        onEvent,
                                        state.userAppliedFormDetails
                                    )
                                }

                            }
                        }
                    }

                }

            }


        })
    }
}

@Composable
fun FormRequestItem(
    paidPaymentDetails: PaidPaymentDetails,
    onEvent: (event: FormRequestEvent) -> Unit,
    userAppliedFormDetails: FormRequestJobDetails
) {

    var showReceipt by remember {
        mutableStateOf(false)
    }
    var showDismissButton by remember {
        mutableStateOf(false)
    }
    if (showReceipt) {
        Dialog(
            onDismissRequest = {
                showReceipt = false
            }, properties = DialogProperties()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
            ) {
                Box() {
                    AsyncImage(
                        modifier = Modifier.clickable {
                            showDismissButton = !showDismissButton
                        },
                        model = paidPaymentDetails.receiptLink,
                        contentDescription = "receipt",
                        contentScale = ContentScale.Fit
                    )
                    if (showDismissButton) {
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.TopEnd),
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    showReceipt = false
                                }) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                            }
                        }

                    }
                }
            }


        }
    }
    var showAlertConfirmedPaymentDialog by remember {
        mutableStateOf(false)
    }
    if (showAlertConfirmedPaymentDialog) {
        Dialog(
            onDismissRequest = {
                showReceipt = false
            }, properties = DialogProperties()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
            ) {
                Card() {
                    Icon(
                        modifier = Modifier
                            .size(70.dp)
                            .padding(10.dp),
                        imageVector = Icons.Filled.VerifiedUser,
                        contentDescription = "Icon"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(0.5f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            onClick = {
                                onEvent(
                                    FormRequestEvent.OnPaymentVerified(
                                        user = paidPaymentDetails.emailAddress,
                                        videoId = paidPaymentDetails.videoId
                                    )
                                )
                                showAlertConfirmedPaymentDialog = false
                            }) {
                            Text(text = "Confirmed")
                        }
                        Button(modifier = Modifier
                            .weight(0.5f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.error),
                            onClick = {
                                showAlertConfirmedPaymentDialog = false
                            }) {
                            Text(
                                modifier = Modifier.background(MaterialTheme.colorScheme.error),
                                text = "Cancel")
                        }

                    }
                }
            }


        }
    }

    var showJobDetails by remember {
        mutableStateOf(false)
    }
    if (showJobDetails){
        FormRequestJobDetailsDialog(
            listOfJobs = userAppliedFormDetails) {
            showJobDetails = false
        }
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = paidPaymentDetails.videoId,
            style = MaterialTheme.typography.titleLarge
        )
        Divider()
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Card(
                    modifier = Modifier.padding(5.dp),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Default.ManageAccounts,
                            contentDescription = "Profile"
                        )

                    }
                }
                Text(
                    text = paidPaymentDetails.emailAddress,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Card(modifier = Modifier.padding(5.dp), elevation = CardDefaults.cardElevation(10.dp)) {
                IconButton(onClick = {
                    showReceipt = !showReceipt
                }) {
                    Icon(
                        imageVector = if (showReceipt) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = ""
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(0.5f),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                IconButton(onClick = {
                    onEvent(FormRequestEvent.OnJobDetailsClicked(paidPaymentDetails.emailAddress,paidPaymentDetails.videoId))
                    showJobDetails = true
                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Default.Details,
                            contentDescription = ""
                        )
                        Text(text = "Job Details")
                    }

                }
            }
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(0.5f),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                IconButton(onClick = {
                    if (!paidPaymentDetails.approved){
                        showAlertConfirmedPaymentDialog = true
                    }

                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (paidPaymentDetails.approved){
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Default.Check,
                                contentDescription = ""
                            )
                            Text(text = "Paid")
                        }else{
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Default.PendingActions,
                                contentDescription = ""
                            )
                            Text(text = "Pending")
                        }

                    }
                }

            }
        }
    }
}
