@file:OptIn(ExperimentalFoundationApi::class)

package com.chetan.jobnepal.screens.user.dashboard.myForm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.models.dashboard.UploadAppliedFormDataRequest
import com.chetan.jobnepal.screens.user.dashboard.DashboardEvent
import com.chetan.jobnepal.screens.user.dashboard.DashboardState
import com.chetan.jobnepal.ui.component.dialogs.PaymentDialog
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal
import com.chetan.jobnepal.utils.downloader.AndroidDownloader
import com.chetan.jobnepal.utils.youtubePlayer.WebContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyForm(
    state: DashboardState,
    onEvent: (event: DashboardEvent) -> Unit,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val list = listOf(stringResource(R.string.pay_now), stringResource(R.string.paid),
        stringResource(
            R.string.admit_card
        )
    )
    val pagerState = rememberPagerState(initialPage = 0) { 3 }


    var paymentId by remember {
        mutableStateOf("")
    }
    if (state.showPaymentDialog) {
        PaymentDialog(
            paymentMethods = state.paymentMethods,
            videoId = paymentId,
            onEvent = onEvent
        ) {
            onEvent(DashboardEvent.OnShowPaymentDialog(it))
        }
    }
    val downloader = AndroidDownloader(ctx)
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        divider = {}
    ) {
        list.forEachIndexed { index, page ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = page,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = if (pagerState.currentPage == index) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        when (page) {
            0 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp)
                ) {
                    MyFormItem(
                        state.appliedListResponse.filter { it.apply == "applied" },
                        listOf(
                            Triple("Pay", Icons.Default.QrCode, true),
                            Triple("Chat",Icons.Default.Chat, true),
                            Triple("Cancel", Icons.Default.Cancel, true)

                        )
                    ){ item, id ->
                        when  (item){
                            "Pay" -> {
                                paymentId = id
                                onEvent(DashboardEvent.OnShowPaymentDialog(true))
                            }
                            "Chat" -> {
                                navController.navigate(Destination.Screen.UserChat.route.replace(
                                    "{vid}",id
                                ))
                            }
                            "Cancel" ->{
                                onEvent(DashboardEvent.DeleteAppliedData(id))
                            }
                        }
                    }
                }
            }

            1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp)
                ) {
                    MyFormItem(
                        state.appliedListResponse.filter { it.apply == "paid" },
                        listOf(
                            Triple("Chat", Icons.Default.Chat, true),
                        )
                    ){ item, id ->
                        when  (item){
                            "Chat" -> {
                                navController.navigate(Destination.Screen.UserChat.route.replace(
                                    "{vid}",id
                                ))
                            }

                        }
                    }
                }
            }
            2 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp)
                ) {
                    MyFormItem(
                        state.appliedListResponse.filter { it.apply == "done" },
                        listOf(
                            Triple("Download", Icons.Default.Download, true),
                            Triple("Chat", Icons.Default.Chat, true),
                        )
                    ){ item, id ->
                        when  (item){
                            "Download" -> {
                                val data = state.appliedListResponse.find { it.id == id }
                                downloader.downloadFile("https://firebasestorage.googleapis.com/v0/b/jobnepal-674cd.appspot.com/o/chtankhadka12%2FAcademic%2FSEE%2F1000012120174?alt=media&token=79166fd8-1093-40c6-a22d-1b9472820062",data?.title?:"")
                            }
                            "Chat" -> {
                                navController.navigate(Destination.Screen.UserChat.route.replace(
                                    "{vid}",id
                                ))
                            }
                        }

                    }
                }
            }

        }
    }
}

@Composable
fun MyFormItem(
    data: List<UploadAppliedFormDataRequest>,
    listOfDropdownItem: List<Triple<String, ImageVector, Boolean>>,
    onClickedDropdownItem: (item: String, id: String) -> Unit
    ) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(data.size) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .drawBehind {
                            drawLine(
                                color = Color.White, // Set the desired color of the border
                                start = Offset(
                                    0f,
                                    size.height
                                ), // Starting point at the bottom-left corner
                                end = Offset(
                                    size.width,
                                    size.height
                                ), // Ending point at the bottom-right corner
                                strokeWidth = 1.dp.toPx(), // Set the desired width of the border
                            )
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = data[it].title)
                    DropdownJobNepal(
                        listOfDropdownItem
                    ) {item ->
                        onClickedDropdownItem(item, data[it].id)
                    }
                }
                WebContent(videoId = data[it].videoLink, modifier = Modifier.height(380.dp))
//                AsyncImage(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentScale = ContentScale.FillWidth,
//                    model = data[it].videoLink,
//                    contentDescription = "details",
//                    alignment = Alignment.Center
//                )
            }
        }
    }

}
sealed class PagerItem()