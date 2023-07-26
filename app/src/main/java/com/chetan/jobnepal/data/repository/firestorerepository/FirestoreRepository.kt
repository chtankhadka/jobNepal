package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.profile.UploadProfileParam

interface FirestoreRepository {

    //dashboard video link

    suspend fun createJobNepalCollection(
        jobNepalColletion : List<String>
    ) : Resource<Any>
    suspend fun uploadNewVideoLink(
        data : UploadNewVideoLink
    ): Resource<Any>
    suspend fun getNewVideoLink() : Resource<UploadNewVideoLink>

    //academic
    suspend fun uploadAcademicData(
        data: UploadAcademicList,
        selectedLevel: String
    ): Resource<Any>

    suspend fun getAcademicData() : Resource<UploadAcademicList>

    suspend fun deleteAcademicData(level: String, names: List<String>): Resource<Any>

    suspend fun uploadProfileData(
        data: UploadProfileParam
    ): Resource<Any>

    suspend fun getProfileData() : Resource<UploadProfileParam>

    // get applied form data
    suspend fun uploadAppliedFormData(
        data: FormAppliedList
    ) : Resource<Any>
    suspend fun getAppliedFormData() : Resource<FormAppliedList>

}