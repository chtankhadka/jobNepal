package com.chetan.jobnepal.screens.onboardscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chetan.jobnepal.utils.LoadLottieAnimation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
@Composable
fun OnBoardScreen(onComplete: () -> Unit, state: OnBoardState, onEvent: (OnBoardEvent) -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val isNextVisible = remember {
        derivedStateOf {
            pagerState.currentPage != state.data.size - 1
        }
    }
    val isPrevVisible = remember {
        derivedStateOf {
            pagerState.currentPage != state.currentPageNumber
        }
    }

    val imageVisibility by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = imageVisibility,
            enter = fadeIn(animationSpec = tween(2000)),
            exit = fadeOut(animationSpec = tween(2000))
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(.75f),
                count = state.data.size,
                state = pagerState,
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.data[currentPage].title,
                        style = MaterialTheme.typography.h4
                    )
                    LoadLottieAnimation(
                        modifier = Modifier.size(300.dp),
                        image = state.data[currentPage].image
                    )
                    Text(
                        text = state.data[currentPage].description,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(26.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isPrevVisible.value) {
                Button(onClick = {
                    scope.launch {
                        onEvent(OnBoardEvent.SwithchPage(state.currentPageNumber - 1 ))
                    }
                }) {
                    Text(
                        text = "Prev"
                    )
                }
            }

            if (isNextVisible.value) {
                Button(onClick = {
                    scope.launch {
                        onEvent(OnBoardEvent.SwithchPage(state.currentPageNumber + 1))
                    }
                }) {
                    Text(
                        text = " Next"
                    )
                }
            }
            if (!isNextVisible.value) {
                Button(onClick = {
                    onComplete()
                }) {
                    Text(
                        text = "Get Started"
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun OnboardSample() {
    OnBoardScreen(state = OnBoardState(), onEvent = {}, onComplete = {})
}