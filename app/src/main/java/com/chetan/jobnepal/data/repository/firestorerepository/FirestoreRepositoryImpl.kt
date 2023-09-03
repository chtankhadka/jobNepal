package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.academic.UploadAcademicData
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse
import com.chetan.jobnepal.data.models.adminpayment.PaidPaymentDetails
import com.chetan.jobnepal.data.models.comment.UserCommentModel
import com.chetan.jobnepal.data.models.dashboard.UploadAppliedFormDataRequest
import com.chetan.jobnepal.data.models.formrequest.FormRequestJobDetails
import com.chetan.jobnepal.data.models.oneSignal.OneSignalId
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.param.UserDashboardUpdateNoticeRequestResponse
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.data.models.searchhistory.SearchHistoryRequestResponse
import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.onesignal.OneSignal
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val preference: Preference
) : FirestoreRepository {
    override suspend fun createJobNepalCollection(jobNepalColletion: List<String>): Resource<Any> {
        return try {

            val collectionRef = firestore.collection(preference.dbTable.toString())
            val check = collectionRef.document("academic").get().await()
            if (check.data == null) {
                val batch = firestore.batch()
                for (documentId in jobNepalColletion) {
                    val documentRef = collectionRef.document(documentId)
                    batch.set(documentRef, emptyMap<String, Any>())
                }
                batch.commit().await()
            } else {

            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadNewVideoLink(data: UploadNewVideoLink): Resource<Any> {
        return try {
                firestore.collection("chtankhadka12")
                .document("videoList")
                .collection("data")
                .document(data.id)
                .set(data)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getNewVideoLink(): Resource<List<UploadNewVideoLink>> {
        return try {
            val videoList = mutableListOf<UploadNewVideoLink>()
            val documnetRef = firestore.collection("chtankhadka12")
                .document("videoList")
                .collection("data")
                .get()
                .await()
            for (document in documnetRef.documents){
                val data = document.toObject<UploadNewVideoLink>()
                data?.let {
                    videoList.add(data)
                }
            }
            Resource.Success(videoList.reversed())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadAcademicData(
        data: UploadAcademicData
    ): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString())
                    .document("academic")
                    .collection(data.level)
                    .document(data.id)
                    .set(data)
                    .await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAcademicData(level: String): Resource<List<UploadAcademicData>> {
        return try {
            val academicData = mutableListOf<UploadAcademicData>()
            val documentRef =
                firestore.collection(preference.dbTable.toString())
                    .document("academic")
                    .collection(level)
                    .get()
                    .await()
            for (document in documentRef.documents) {
                val data = document.toObject<UploadAcademicData>()
                data?.let {
                    academicData.add(it)
                }
            }
            println(academicData)
            Resource.Success(academicData)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun deleteAcademicData(level: String): Resource<Any> {
        return try {
            val collectionRef = firestore.collection(preference.dbTable.toString())
                .document("academic")
                .collection(level)

            val querySnapshot = collectionRef.get().await()

            for (documentSnapshot in querySnapshot.documents) {
                val documentReference = documentSnapshot.reference
                documentReference.delete().await()
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun deleteAcademicSingleAttachement(id: String, level: String): Resource<Any> {
        return try {
            firestore.collection(preference.dbTable.toString())
                .document("academic")
                .collection(level)
                .document(id)
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadProfileData(data: UploadProfileParam): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("Profile")
            val result = documentRef.set(data).await()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProfileData(): Resource<UploadProfileParam> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("Profile")
            val result = documentRef.get().await().toObject(UploadProfileParam::class.java)
            if (result != null) {
                Resource.Success(result)
            } else {
                Resource.Failure(java.lang.Exception("No Data yet"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadAppliedFormData(
        data: UploadAppliedFormDataRequest
    ): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString())
                    .document("appliedList")
                    .collection("data")
                    .document(data.id)
                    .set(data)
                    .await()

            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun deleteAppliedFormData(id: String): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("appliedList")
                    .collection("data").document(id).delete().await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAppliedFormData(): Resource<List<UploadAppliedFormDataRequest>> {
        return try {
            val appliedList = mutableListOf<UploadAppliedFormDataRequest>()
            val result = firestore.collection(preference.dbTable.toString()).document("appliedList")
                .collection("data").get().await()
            for (document in result.documents) {
                val data = document.toObject<UploadAppliedFormDataRequest>()
                data?.let {
                    appliedList.add(it)
                }
            }
            Resource.Success(appliedList.reversed())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getSearchHistory(): Resource<List<SearchHistoryRequestResponse.DataColl>> {
        return try {
            val result =
                firestore.collection(preference.dbTable.toString()).document("searchHistory").get()
                    .await().toObject(SearchHistoryRequestResponse::class.java)
            if (result != null) {
                Resource.Success(result.dataColl.reversed())
            } else {
                Resource.Failure(java.lang.Exception("No Data yet"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun postSearchHistory(data: SearchHistoryRequestResponse): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("searchHistory")
            val newData = mapOf(
                "dataColl" to FieldValue.arrayUnion(*data.dataColl.toTypedArray())
            )
            val result = documentRef.update(newData).await()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun deleteSearchHistory(data: SearchHistoryRequestResponse): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("searchHistory")
                    .set(data).await()
            Resource.Success(documentRef)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUpdatedNoticeUserDashboard(): Resource<UserDashboardUpdateNoticeRequestResponse> {
        return try {
            val result =
                firestore.collection("chtankhadka12").document("updateNotice").get().await()
                    .toObject(UserDashboardUpdateNoticeRequestResponse::class.java)
            if (result != null) {
                Resource.Success(result)
            } else {
                Resource.Failure(java.lang.Exception("No Data yet"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getPaymentMethod(): Resource<List<AddAdminPaymentMethodResponse>> {
        return try {
            val paymentList = mutableListOf<AddAdminPaymentMethodResponse>()
            val query =
                firestore.collection("chtankhadka12").document("paymentMethod").collection("data")
                    .get().await()
            for (document in query.documents) {
                val bank = document.toObject<AddAdminPaymentMethodResponse>()
                bank?.let {
                    paymentList.add(it)
                }
            }

            Resource.Success(paymentList.reversed())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun requestPaidReceipt(data: PaidPaymentDetails): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection("chtankhadka12").document("paidReceipt").collection("videoId")
                    .document(data.videoId).collection("data").document(data.emailAddress).set(data)
                    .await()

            val dummy =
                firestore.collection("chtankhadka12").document("paidReceipt").collection("videoId")
                    .document(data.videoId).set(mapOf("test" to "")).await()

            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun setUserComment(data: UserCommentModel): Resource<Any> {
        return try {
            firestore.collection("chtankhadka12")
                .document("videoList")
                .collection("data")
                .document(data.videoId)
                .collection("comment")
                .document(data.commentId)
                .set(data)
                .await()
            Resource.Success(Unit)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUsersComment(videoId: String): Resource<List<UserCommentModel>> {
        return try {
            val commentList = mutableListOf<UserCommentModel>()

            val doumentRef = firestore.collection("chtankhadka12")
                .document("videoList")
                .collection("data")
                .document(videoId)
                .collection("comment")
                .get()
                .await()
            for (document in doumentRef.documents){
                val data = document.toObject<UserCommentModel>()
                data?.let {
                    commentList.add(data)
                }
            }

            Resource.Success(commentList)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    //admin
    override suspend fun updateNoticeUserDashboard(data: UserDashboardUpdateNoticeRequestResponse): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection("chtankhadka12").document("updateNotice").set(data).await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addAdminPaymentMethod(data: AddAdminPaymentMethodResponse): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection("chtankhadka12").document("paymentMethod").collection("data")
                    .document(data.bankName).set(data).await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUserPaymentFormRequest(docsId: String): Resource<List<PaidPaymentDetails>> {
        return try {
            val paidReceiptList = mutableListOf<PaidPaymentDetails>()
            val documentRef =
                firestore.collection("chtankhadka12").document("paidReceipt").collection("videoId")
                    .document(docsId).collection("data").get().await()
            for (document in documentRef.documents) {
                val receipt = document.toObject<PaidPaymentDetails>()
                receipt?.let {
                    paidReceiptList.add(it)
                }
            }
            Resource.Success(paidReceiptList.reversed())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun changeFormRequestToPaid(user: String, videoId: String): Resource<Any> {
        return try {
            firestore.collection(user).document("appliedList")
                .collection("data").document(videoId)
                .update("apply", "paid").await()
            firestore.collection("chtankhadka12").document("paidReceipt").collection("videoId")
                .document(videoId).collection("data").document(user).update("approved", true)
                .await()
            Resource.Success("Success")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAppliedFormDetails(
        user: String,
        videoId: String
    ): Resource<FormRequestJobDetails> {
        return try {
            val formDetails: FormRequestJobDetails
            val documentRef = firestore.collection(user)
                .document("appliedList")
                .collection("data")
                .document(videoId)
                .get()
                .await()
                .toObject<FormRequestJobDetails>()
            formDetails = documentRef ?: FormRequestJobDetails()
            Resource.Success(formDetails)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUserPaymentVideoIdList(): Resource<List<String>> {
        return try {
            val videoIdList = mutableListOf<String>()
            val documentRef =
                firestore.collection("chtankhadka12").document("paidReceipt").collection("videoId")
                    .get().await()
            for (docId in documentRef.documents) {
                videoIdList.add(docId.id)
            }
            println(documentRef.documents.size)
            println(videoIdList)

            Resource.Success(videoIdList)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    // oneSignal Notification
    override suspend fun saveNotification(data: StoreNotificationRequestResponse): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("notificationData")
                    .collection("data").document(data.time).set(data).await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getNotification(): Resource<List<StoreNotificationRequestResponse>> {
        return try {
            var response = mutableListOf<StoreNotificationRequestResponse>()
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("notificationData")
                    .collection("data").get().await()
            for (document in documentRef.documents) {
                val notification = document.toObject<StoreNotificationRequestResponse>()
                notification?.let {
                    response.add(it)
                }
            }
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getOneSignalUserId() {
        try {
            val documentSnapshot =
                firestore.collection(preference.dbTable.toString()).document("oneSignalIdentity")
                    .get().await().toObject<OneSignalId>()

            if (!documentSnapshot?.oneSignalId.isNullOrBlank()) {
                println("I am here at ff ${documentSnapshot?.oneSignalId}")
                OneSignal.setExternalUserId(documentSnapshot.toString())
            } else {
                val newId = OneSignal.getDeviceState()?.userId
                println("I am new id $newId")
                if (newId != null) {
                    val newIdMap = mapOf("oneSignalId" to newId)
                    firestore.collection(preference.dbTable.toString())
                        .document("oneSignalIdentity").set(newIdMap).await()
                    OneSignal.sendTag("id", newId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}