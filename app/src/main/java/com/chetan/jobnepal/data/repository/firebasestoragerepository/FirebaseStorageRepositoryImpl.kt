package com.chetan.jobnepal.data.repository.firebasestoragerepository

import android.net.Uri
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseStorageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val storageRef: StorageReference,
    private val preference: Preference
) : FirebaseStorageRepository {

    override suspend fun uploadAcademicAttachement(fileUri: List<Uri>): Resource<List<String>> {
        return try {
            val uploadedImageUris = mutableListOf<String>()
            withContext(Dispatchers.IO){
                val deferredUpload = fileUri.map { uri ->
                    async {
                        val file = File(uri.path!!)
                        val imageRef = storageRef.child(preference.dbTable+"/Images/"+file.name)
                        try {
                            val uploadTask = imageRef.putFile(uri).await()
                            val downloadUrl = uploadTask.storage.downloadUrl.await()
                            uploadedImageUris.add(downloadUrl.toString())
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
                deferredUpload.awaitAll()
            }
            Resource.Success(uploadedImageUris)

        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}