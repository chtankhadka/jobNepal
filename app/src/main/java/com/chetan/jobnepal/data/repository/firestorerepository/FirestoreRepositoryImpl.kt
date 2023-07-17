package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.param.UploadAcademicList
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.screens.academic.AcademicState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preference: Preference
) : FirestoreRepository {
    override suspend fun uploadNewVideoLink(data: UploadNewVideoLink): Resource<Any> {
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document(data.id)

            val newData = mapOf(
                "data" to FieldValue.arrayUnion(*data.data.toTypedArray())
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
            val result = firestore.collection(preference.dbTable.toString())
                .get()
                .await()
                .toObjects(UploadNewVideoLink.DataColl::class.java)
            Resource.Success(result)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadAcademicData(
        data: UploadAcademicList,
        selectedLevel: String
    ): Resource<Any> {
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString())
                .document("academic")
            val newData = when (selectedLevel) {
                AcademicState.SLC_SEE -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.SEE.toTypedArray()))
                }

                AcademicState.IAC -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.IAC.toTypedArray()))
                }

                AcademicState.BSC_CSIT -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.BSC_CSIT.toTypedArray()))
                }

                AcademicState.BAC -> {
                    mapOf(selectedLevel to FieldValue.arrayUnion(*data.IAC.toTypedArray()))
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
            val result = firestore.collection(preference.dbTable.toString())
                .document("academic")
                .get()
                .await()
                .toObject(UploadAcademicList::class.java)
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
}