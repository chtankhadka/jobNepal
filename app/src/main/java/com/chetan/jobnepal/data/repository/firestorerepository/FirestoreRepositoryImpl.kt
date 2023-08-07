package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.data.models.searchhistory.SearchHistoryRequestResponse
import com.chetan.jobnepal.screens.academic.AcademicState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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
            val batch = firestore.batch()
            for (documentId in jobNepalColletion) {
                val documentRef = collectionRef.document(documentId)
                batch.set(documentRef, emptyMap<String, Any>())
            }
            batch.commit().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadNewVideoLink(data: UploadNewVideoLink): Resource<Any> {
        return try {
            val documentRef = firestore.collection("chtankhadka12").document("videoList")
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

    override suspend fun deleteAcademicData(level: String ): Resource<Any> {
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

    override suspend fun uploadAppliedFormData(data: FormAppliedList): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("appliedList")
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
                    val deleteData = dataList.removeAt(itemIndex)
                    documentRef.update("dataColl", deleteData).await()
                }

            }
            Resource.Success("deleteData")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAppliedFormData(): Resource<List<FormAppliedList.DataColl>> {
        return try {
            val result = firestore.collection(preference.dbTable.toString())
                .document("appliedList")
                .get()
                .await()
                .toObject(FormAppliedList::class.java)
            if (result != null) {
                Resource.Success(result.dataColl)
            } else {
                Resource.Failure(java.lang.Exception("No Data yet"))
            }
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
    override suspend fun deleteSearchHistory(data: SearchHistoryRequestResponse):Resource<Any>{
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document("searchHistory")
                .set(data)
                .await()
            Resource.Success(documentRef)

        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}