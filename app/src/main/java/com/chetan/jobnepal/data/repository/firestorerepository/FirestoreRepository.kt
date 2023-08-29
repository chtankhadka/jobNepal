package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.dashboard.UploadAppliedFormDataRequest
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


    //Admin
    suspend fun updateNoticeUserDashboard(
        data: UserDashboardUpdateNoticeRequestResponse
    ): Resource<Any>


    //for oneSignal notification
    suspend fun saveNotification(
        data: StoreNotificationRequestResponse
    ): Resource<Any>

    suspend fun getNotification(
    ): Resource<List<StoreNotificationRequestResponse>>

    suspend fun getOneSignalUserId(
    )

}