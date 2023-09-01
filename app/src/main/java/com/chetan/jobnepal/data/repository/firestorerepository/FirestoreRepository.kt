package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodRequest
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse
import com.chetan.jobnepal.data.models.adminpayment.PaidPaymentDetails
import com.chetan.jobnepal.data.models.dashboard.UploadAppliedFormDataRequest
import com.chetan.jobnepal.data.models.formrequest.FormRequestJobDetails
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.param.UserDashboardUpdateNoticeRequestResponse
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.data.models.searchhistory.SearchHistoryRequestResponse
import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse

interface FirestoreRepository {

    //dashboard video link

    suspend fun createJobNepalCollection(
        jobNepalColletion: List<String>
    ): Resource<Any>

    suspend fun uploadNewVideoLink(
        data: UploadNewVideoLink
    ): Resource<Any>

    suspend fun getNewVideoLink(): Resource<List<UploadNewVideoLink.DataColl>>

    //academic
    suspend fun uploadAcademicData(
        data: UploadAcademicList,
        selectedLevel: String
    ): Resource<Any>

    suspend fun getAcademicData(): Resource<UploadAcademicList>

    suspend fun deleteAcademicData(level: String): Resource<Any>

    suspend fun uploadProfileData(
        data: UploadProfileParam
    ): Resource<Any>

    suspend fun getProfileData(): Resource<UploadProfileParam>

    // get applied form data
    suspend fun uploadAppliedFormData(
        data: UploadAppliedFormDataRequest,
        id: String
    ): Resource<Any>

    suspend fun deleteAppliedFormData(
        id: String
    ): Resource<Any>

    suspend fun getAppliedFormData(): Resource<List<UploadAppliedFormDataRequest>>

    suspend fun getSearchHistory(): Resource<List<SearchHistoryRequestResponse.DataColl>>
    suspend fun postSearchHistory(
        data: SearchHistoryRequestResponse
    ): Resource<Any>

    suspend fun deleteSearchHistory(
        data: SearchHistoryRequestResponse
    ): Resource<Any>

    suspend fun getUpdatedNoticeUserDashboard(
    ): Resource<UserDashboardUpdateNoticeRequestResponse>
    suspend fun getPaymentMethod(): Resource<List<AddAdminPaymentMethodResponse>>
    suspend fun requestPaidReceipt(
        data: PaidPaymentDetails
    ): Resource<Any>

    //Admin
    suspend fun updateNoticeUserDashboard(
        data: UserDashboardUpdateNoticeRequestResponse
    ): Resource<Any>

    suspend fun addAdminPaymentMethod(
        data: AddAdminPaymentMethodResponse
    ): Resource<Any>

    suspend fun getUserPaymentFormRequest(docsId: String) : Resource<List<PaidPaymentDetails>>
    suspend fun changeFormRequestToPaid(
        user: String,
        videoId: String
    ) : Resource<Any>
    suspend fun getAppliedFormDetails(
        user: String,
        videoId: String
    ): Resource<FormRequestJobDetails>
    suspend fun getUserPaymentVideoIdList() : Resource<List<String>>


    //for oneSignal notification
    suspend fun saveNotification(
        data: StoreNotificationRequestResponse
    ): Resource<Any>

    suspend fun getNotification(
    ): Resource<List<StoreNotificationRequestResponse>>

    suspend fun getOneSignalUserId(
    )

}