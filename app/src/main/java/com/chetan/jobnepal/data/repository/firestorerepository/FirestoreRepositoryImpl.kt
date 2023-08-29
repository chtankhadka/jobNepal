package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.dashboard.UploadAppliedFormDataRequest
import com.chetan.jobnepal.data.models.oneSignal.OneSignalId
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.param.UserDashboardUpdateNoticeRequestResponse
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.data.models.searchhistory.SearchHistoryRequestResponse
import com.chetan.jobnepal.data.models.storenotification.StoreNotificationRequestResponse
import com.chetan.jobnepal.screens.user.academic.AcademicState
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
            val documentRef = firestore.collection("chtankhadka12")
                .document("videoList")
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

    override suspend fun getNewVideoLink(): Resource<List<UploadNewVideoLink.DataColl>> {
        return try {
            val result = firestore.collection("chtankhadka12")
                .document("videoList")
                .get()
                .await()
                .toObject(UploadNewVideoLink::class.java)
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

    override suspend fun uploadAcademicData(
        data: UploadAcademicList, selectedLevel: String
    ): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("academic")
            val newData = when (selectedLevel) {
                AcademicState.SLC_SEE -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.SEE.toTypedArray()))
                }

                AcademicState.IAC -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.IAC.toTypedArray()))
                }

                AcademicState.BSc_CSIT -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.BSc_CSIT.toTypedArray()))
                }

                AcademicState.BAC -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.IAC.toTypedArray()))
                }

                AcademicState.CITIZENSHIP -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.CITIZENSHIP.toTypedArray()))
                }

                AcademicState.EXPERIENCE -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.experience.toTypedArray()))
                }

                AcademicState.TRAINING -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.training.toTypedArray()))
                }

                else -> {
                    mapOf()
                }
            }

            val result = documentRef.update(newData).await()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAcademicData(): Resource<UploadAcademicList> {
        return try {
            val result =
                firestore.collection(preference.dbTable.toString()).document("academic").get()
                    .await().toObject(UploadAcademicList::class.java)
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

    override suspend fun deleteAcademicData(level: String): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString())
                    .document("academic")

            val newData =
                //IAC: [{url:"https:"}]
                mapOf(level to FieldValue.delete())
//                mapOf("IAC" to FieldValue.arrayRemove("https:"))
            documentRef.update(newData).await()
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
        data: UploadAppliedFormDataRequest,
        id: String
    ): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString())
                    .document("appliedList")
                    .collection("data")
                    .document(id)
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
            val snapshot = documentRef.get().await()
            if (snapshot != null) {
                val dataList =
                    snapshot.toObject(FormAppliedList::class.java)?.dataColl?.toMutableList()
                val itemIndex = dataList?.indexOfFirst { it.id == id }
                if (itemIndex != null && itemIndex != -1) {
                    dataList.removeAt(itemIndex)
                    documentRef.update("dataColl", dataList).await()
                }

            }
            Resource.Success("deleteData")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAppliedFormData(): Resource<List<UploadAppliedFormDataRequest>> {
        return try {
            val appliedList = mutableListOf<UploadAppliedFormDataRequest>()
            val result = firestore.collection(preference.dbTable.toString())
                .document("appliedList")
                .collection("data")
                .get()
                .await()
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
                firestore.collection(preference.dbTable.toString())
                    .document("searchHistory")
                    .get()
                    .await()
                    .toObject(SearchHistoryRequestResponse::class.java)
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
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document("searchHistory")
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
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document("searchHistory")
                .set(data)
                .await()
            Resource.Success(documentRef)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getUpdatedNoticeUserDashboard(): Resource<UserDashboardUpdateNoticeRequestResponse> {
        return try {
            val result = firestore.collection("chtankhadka12")
                .document("updateNotice")
                .get()
                .await()
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

    //admin
    override suspend fun updateNoticeUserDashboard(data: UserDashboardUpdateNoticeRequestResponse): Resource<Any> {
        return try {
            val documentRef = firestore.collection("chtankhadka12")
                .document("updateNotice")
                .set(data)
                .await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


    // oneSignal Notification
    override suspend fun saveNotification(data: StoreNotificationRequestResponse): Resource<Any> {
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document("notificationData")
                .collection("data")
                .document(data.time)
                .set(data)
                .await()
            Resource.Success(documentRef)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getNotification(): Resource<List<StoreNotificationRequestResponse>> {
        return try {
            var response = mutableListOf<StoreNotificationRequestResponse>()
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document("notificationData")
                .collection("data")
                .get()
                .await()
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
            val documentSnapshot = firestore.collection(preference.dbTable.toString())
                .document("oneSignalIdentity")
                .get()
                .await()
                .toObject<OneSignalId>()

            if (!documentSnapshot?.oneSignalId.isNullOrBlank()) {
                println("I am here at ff ${documentSnapshot?.oneSignalId}")
                OneSignal.setExternalUserId(documentSnapshot.toString())
            } else {
                val newId = OneSignal.getDeviceState()?.userId
                println("I am new id $newId")
                if (newId != null) {
                    val newIdMap = mapOf("oneSignalId" to newId)
                    firestore.collection(preference.dbTable.toString())
                        .document("oneSignalIdentity")
                        .set(newIdMap)
                        .await()
                    OneSignal.sendTag("id", newId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}