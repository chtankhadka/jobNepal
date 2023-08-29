package com.chetan.jobnepal.screens.admin.adminbottomsheet



sealed interface AdminBottomSheetEvent{
    object UpdateBottomSheetNotice : AdminBottomSheetEvent
    data class OnDiscountPercentageChange(val value: String): AdminBottomSheetEvent
    data class OnTotalCostChange(val value: String) : AdminBottomSheetEvent
    object DissmissInfoMsg : AdminBottomSheetEvent
}