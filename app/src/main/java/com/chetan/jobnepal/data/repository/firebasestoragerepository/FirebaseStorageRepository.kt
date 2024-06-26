package com.chetan.jobnepal.data.repository.firebasestoragerepository

import android.net.Uri
import com.chetan.jobnepal.data.Resource

interface FirebaseStorageRepository {


    //admin
    suspend fun uploadPaymentMethod(fileUri: List<Uri>,bankName: String):  Resource<List<Pair<String,String>>>

    suspend fun adminUploadReceipt(
        user: String,
        fileUri: Uri
    ): Resource<Pair<String, String>>

    suspend fun uploadAcademicAttachement(fileUri: Uri,level: String) : Resource<Pair<String,String>>
    suspend fun deleteAcademicAtachements(level: String, ids: List<String>) : Resource<Any>
    suspend fun deleteAcademicSingleAttachement(id: String, level: String): Resource<Any>

    //payment
    suspend fun uploadPaidReceipt(fileUri: Uri) : Resource<Pair<String,String>>
    suspend fun uploadSelfPaidBankBoucher(fileUri: Uri) : Resource<Pair<String,String>>

    //profile data
    suspend fun uploadProfileImage(fileUri: Uri): Resource<Pair<String,String>>
}