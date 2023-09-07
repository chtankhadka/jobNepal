package com.chetan.jobnepal.screens.user.dashboard

import android.net.Uri
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

sealed interface DashboardEvent {
    class SelectVideo(val value: String): DashboardEvent
    data object ApplyNow : DashboardEvent
    class OnAppliedJobDescriptionChange(val value: String) : DashboardEvent
    class ShowApplyDialog(val show: Boolean, val id: String) : DashboardEvent
    class JobsForDialog(val value : UploadNewVideoLink): DashboardEvent
    class DeleteAppliedData(val value: String) : DashboardEvent
    class ChangeLanguage(val value: Boolean): DashboardEvent
    class OnQueryChangeOnSearch(val value: String): DashboardEvent
    class OnQuerySearchOnSearch(val value: String): DashboardEvent
    class OnQuerySearchDelete(val value: String): DashboardEvent
    class OnProvinceFilter(val value: String) : DashboardEvent
    class OnFieldFilter(val value: String) : DashboardEvent
    class OnSubmitReceipt(val videoId: String, val receiptUri: Uri) : DashboardEvent
    class OnShowPaymentDialog(val value: Boolean): DashboardEvent
    class UploadPaidVoucher(val imgUri: Uri, val vid: String): DashboardEvent
    data object OnRefresh : DashboardEvent
//    class OnLikeClicked(val videoId: String): DashboardEvent
//    class GetLikedDetails(val value: String)


    data object DismissInfoMsg : DashboardEvent
    data object Logout : DashboardEvent


}