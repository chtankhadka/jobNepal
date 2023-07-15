package com.chetan.jobnepal.data.repository.firebasestoragerepository

import android.net.Uri
import com.chetan.jobnepal.data.Resource

interface FirebaseStorageRepository {

    suspend fun uploadAcademicAttachement(fileUri: List<Uri>) : Resource<List<String>>
}