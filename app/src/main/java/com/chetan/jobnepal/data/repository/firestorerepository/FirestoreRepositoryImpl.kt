package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.academic.UploadAcademicList
import com.chetan.jobnepal.data.models.dashboard.FormAppliedList
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.screens.academic.AcademicState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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
            val documentRef = firestore.collection(preference.dbTable.toString()).document("videoList")
            val newData = mapOf(
                "dataColl" to FieldValue.arrayUnion(*data.dataColl.toTypedArray()))
            val result = documentRef.update(newData).await()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getNewVideoLink(): Resource<UploadNewVideoLink> {
        return try {
            val result = firestore.collection(preference.dbTable.toString())
                .document("videoList")
                .get()
                .await()
                .toObject(UploadNewVideoLink::class.java)
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

    override suspend fun deleteAcademicData(level: String, names: List<String>): Resource<Any> {
        return try {
            val documentRef =
                firestore.collection(preference.dbTable.toString()).document("academic")
                    .update("IAC", FieldValue.arrayRemove("1000011340336")).await()


            Resource.Success(Unit)


        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadProfileData(data: UploadProfileParam): Resource<Any> {
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString()).document("Profile")
            val result = documentRef.set(data).await()
            Resource.Success(result)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProfileData(): Resource<UploadProfileParam> {
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString()).document("Profile")
            val result = documentRef.get().await().toObject(UploadProfileParam::class.java)
            if (result != null){
                Resource.Success(result)
            }else{
                Resource.Failure(java.lang.Exception("No Data yet"))
            }

        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadAppliedFormData(data: FormAppliedList): Resource<Any> {
        return try {
            val documentRef = firestore.collection(preference.dbTable.toString()).document("appliedList")

            val newData = mapOf(
                "data" to FieldValue.arrayUnion(*arrayOf(data))
            )
            val result = documentRef.update(newData).await()

            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun getAppliedFormData(): Resource<List<FormAppliedList>> {
        return try {
            val result = firestore.collection(preference.dbTable.toString()).get().await()
                .toObjects(FormAppliedList::class.java)
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}