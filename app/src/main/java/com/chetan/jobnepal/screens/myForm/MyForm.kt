@file:OptIn(ExperimentalFoundationApi::class)

package com.chetan.jobnepal.screens.myForm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyForm(

) {
    val scope = rememberCoroutineScope()
    val list = listOf("Nepal","Gopal")
    val state = rememberPagerState(initialPage = 0) { 2 }
    TabRow(
        selectedTabIndex = state.currentPage,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        divider = {}
    ) {
        list.forEachIndexed { index, page ->
            Tab(
                selected = state.currentPage == index,
                onClick = {
                    scope.launch {
                        state.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = page,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = if (state.currentPage == index) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = state
    ) { page ->
        when (page){
            0 ->{
                Column(modifier = Modifier.fillMaxSize().background(Color.Blue)) {

                }
            }
            1 ->{
                Column(modifier = Modifier.fillMaxSize().background(Color.Green)) {
                }
            }
        }
    }
}

@Preview
@Composable
fun showThis(){
    MyForm()
}