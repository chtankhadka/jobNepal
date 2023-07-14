package com.chetan.jobnepal.data.repository.firestorerepository

import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.param.UploadNewVideoLink
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    val preference: Preference
) : FirestoreRepository {
    override suspend fun uploadNewVideoLink(data: UploadNewVideoLink): Resource<Any> {
        return try {
            val result = firestore.collection(preference.dbTable.toString())
                .document(data.id)
                .set(data)
                .await()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)

        }
    }

    override suspend fun getNewVideoLink(): Resource<List<UploadNewVideoLink>> {
        return try {
            val result = firestore.collection(preference.dbTable.toString())
                .get()
                .await()
                .toObjects(UploadNewVideoLink::class.java)
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}