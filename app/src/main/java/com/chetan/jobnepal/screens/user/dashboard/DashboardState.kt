package com.chetan.jobnepal.screens.user.dashboard


import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.dashboard.UploadAppliedFormDataRequest
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.param.UserDashboardUpdateNoticeRequestResponse
import com.chetan.jobnepal.data.models.searchhistory.SearchHistoryRequestResponse
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class DashboardState(
    val videoListResponse: List<UploadNewVideoLink> = emptyList(),
    val originalVideoListResponse: List<UploadNewVideoLink> = emptyList(),
    val appliedListResponse: List<UploadAppliedFormDataRequest> = emptyList(),
    val jobsForDialog: List<Triple<String, List<String>, List<String>>> = emptyList(),
    val searchListResponse: List<SearchHistoryRequestResponse.DataColl> = emptyList(),
    val nepaliLanguage: Boolean = false,
    val appliedIdsList : List<String> = emptyList(),
    val updatedNotice: UserDashboardUpdateNoticeRequestResponse = UserDashboardUpdateNoticeRequestResponse(),
    val selectedVideoId: String = "",
    val showPaymentDialog: Boolean = false,
    val paymentMethods: List<AddAdminPaymentMethodResponse> = emptyList(),

//    val likedListDetails : Pair<Int,Boolean> = 0 to false,

    val onChangeJobDescription: String = "",
    val showApplyDialog: Boolean = false,
    val searchText: String = "",
    val academicList: List<FormAppliedList.DataColl.AcademicList> = emptyList(),
    val profileUrl: String = "",
    val currentUserName: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
