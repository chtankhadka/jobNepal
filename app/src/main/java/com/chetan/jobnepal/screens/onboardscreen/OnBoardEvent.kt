package com.chetan.jobnepal.screens.onboardscreen

sealed interface OnBoardEvent{
    data class SwithchPage(val pageNumber: Int) : OnBoardEvent
}