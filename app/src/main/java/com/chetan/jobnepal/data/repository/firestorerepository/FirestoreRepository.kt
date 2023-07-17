package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.param.UploadAcademicList
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink

interface FirestoreRepository {

    //dashboard video link
    suspend fun uploadNewVideoLink(
        data : UploadNewVideoLink
    ): Resource<Any>
    suspend fun getNewVideoLink() : Resource<List<UploadNewVideoLink.DataColl>>

    //academic
    suspend fun uploadAcademicData(
        data: UploadAcademicList,
        selectedLevel: String
    ): Resource<Any>

    suspend fun getAcademicData() : Resource<UploadAcademicList>

}