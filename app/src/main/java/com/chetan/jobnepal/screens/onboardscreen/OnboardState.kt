package com.chetan.jobnepal.screens.onboardscreen

import com.chetan.jobnepal.R


data class OnBoardState(val currentPageNumber: Int = 0){
    val data = listOf(
        OnboardPage(R.raw.intor1, "", "Online Forms Wearing You Out?"),
        OnboardPage(R.raw.intro2, "", "Let Us Handle Them!")
    )
    val isLastPage = currentPageNumber == data.size - 1
}
data class OnboardPage(
val image: Int,
val title: String,
val description: String
)
