package com.chetan.jobnepal.data.repository.firebasestoragerepository

import android.net.Uri
import com.chetan.jobnepal.data.Resource

interface FirebaseStorageRepository {


    suspend fun uploadAcademicAttachement(fileUri: List<Uri>,level: String) : Resource<List<Pair<String,String>>>

    suspend fun deleteAcademicAtachements(level: String, names: List<String>) : Resource<Any>
}