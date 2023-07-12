package com.chetan.jobnepal.screens.onboardscreen

import com.chetan.jobnepal.R


data class OnBoardState(val currentPageNumber: Int = 0){
    val data = listOf(
        OnboardPage(R.raw.intro2, "Tittle 1", "Are you Stock at work!!!"),
        OnboardPage(R.raw.intro2, "Title 2", "Lets Start")
    )
    val currentPage = data[currentPageNumber]
    val isFirstPage = currentPageNumber == 0
    val isLastPage = currentPageNumber == data.size - 1
}
data class OnboardPage(
val image: Int,
val title: String,
val description: String
)
