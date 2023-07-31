@file:OptIn(ExperimentalFoundationApi::class)

package com.chetan.jobnepal.screens.dashboard.myForm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.screens.dashboard.DashboardEvent
import com.chetan.jobnepal.screens.dashboard.DashboardState
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyForm(state: DashboardState, onEvent: (event: DashboardEvent) -> Unit) {
    val scope = rememberCoroutineScope()
    val list = listOf("Pending", "Pay Now", "On Going")
    val pagerState = rememberPagerState(initialPage = 0) { 4 }
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
                        state.appliedListResponse.dataColl.filter { it.apply == "applyLater" },
                        listOf(
                            Triple("Apply", Icons.Default.AppRegistration, true),
                            Triple("Full Guid", Icons.Default.Link, true),
                            Triple("Cancel", Icons.Default.Cancel, true)
                        )
                    ){ item, id ->
                        when (item){
                            "Apply" -> {
                                onEvent(DashboardEvent.DeleteAppliedData(id))
                                onEvent(DashboardEvent.ShowApplyDialog(true))
                            }
                            "Cancel" -> {
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
                        state.appliedListResponse.dataColl.filter { it.apply == "applied" },
                        listOf(
                            Triple("Pay", Icons.Default.QrCode, true),
                            Triple("Edit", Icons.Default.Edit, true),
                            Triple("Cancel", Icons.Default.Cancel, true)

                        )
                    ){ item, id ->
                        when  (item){
                            "Pay" -> {
                                onEvent(DashboardEvent.DeleteAppliedData(id))
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
                        state.appliedListResponse.dataColl.filter { it.apply == "paid" },
                        listOf(
                            Triple("Refund", Icons.Default.Undo, true),
                            Triple("Download", Icons.Default.Download, true)

                        )
                    ){ item, id ->

                    }
                }
            }

        }
    }
}

@Composable
fun MyFormItem(
    data: List<FormAppliedList.DataColl>,
    listOfDropdownItem: List<Triple<String, ImageVector, Boolean>>,
    onClickedDropdownItem: (item: String, id: String) -> Unit
    ) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(data.size) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
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
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    model = data[it].videoLink,
                    contentDescription = "details",
                    alignment = Alignment.Center
                )
            }
        }
    }

}