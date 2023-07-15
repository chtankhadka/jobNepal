package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.google.firebase.firestore.QuerySnapshot

interface FirestoreRepository {

    suspend fun uploadNewVideoLink(
        data : UploadNewVideoLink
    ): Resource<Any>
    suspend fun getNewVideoLink() : Resource<List<UploadNewVideoLink.DataColl>>
}