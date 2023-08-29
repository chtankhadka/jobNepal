package com.chetan.jobnepal.data.repository.firebasestoragerepository

import android.net.Uri
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodRequest
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse

interface FirebaseStorageRepository {


    //admin
    suspend fun uploadPaymentMethod(fileUri: List<Uri>,bankName: String):  Resource<List<Pair<String,String>>>
    suspend fun uploadAcademicAttachement(fileUri: List<Uri>,level: String) : Resource<List<Pair<String,String>>>

    suspend fun deleteAcademicAtachements(level: String, names: List<String>) : Resource<Any>

    //profile data
    suspend fun uploadProfileImage(fileUri: Uri): Resource<Pair<String,String>>
}