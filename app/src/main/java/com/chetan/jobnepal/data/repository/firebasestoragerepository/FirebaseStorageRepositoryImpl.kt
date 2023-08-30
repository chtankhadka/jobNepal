package com.chetan.jobnepal.data.repository.firebasestoragerepository

import android.net.Uri
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodRequest
import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodResponse
import com.chetan.jobnepal.utils.GenerateRandomNumber
import com.google.android.gms.tasks.Tasks
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
    override suspend fun uploadPaymentMethod(fileUri: List<Uri>,bankName: String):  Resource<List<Pair<String,String>>> {
        return try {
            val uploadedImageInfo = mutableListOf<Pair<String,String>>()
            withContext(Dispatchers.IO){
                val deferredUpload = fileUri.map { uri ->
                    async {
                        val file = File(uri.path!!)
                        val fileName = file.name+"${GenerateRandomNumber.generateRandomNumber(111..999)}"
                        val imageRef = storageRef.child("chtankhadka12"+"/Payment/${bankName}/"+fileName)
                        try {
                            val uploadTask = imageRef.putFile(uri).await()
                            val downloadUrl = uploadTask.storage.downloadUrl.await()
                            uploadedImageInfo.add(Pair(fileName,downloadUrl.toString()))
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
                deferredUpload.awaitAll()
            }
            Resource.Success(uploadedImageInfo)

        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadAcademicAttachement(fileUri: List<Uri>,level: String): Resource<List<Pair<String,String>>> {
        return try {
            val uploadedImageInfo = mutableListOf<Pair<String,String>>()
            withContext(Dispatchers.IO){
                val deferredUpload = fileUri.map { uri ->
                    async {
                        val file = File(uri.path!!)
                        val fileName = file.name+"${GenerateRandomNumber.generateRandomNumber(111..999)}"
                        val imageRef = storageRef.child(preference.dbTable+"/Academic/${level}/"+fileName)
                        try {
                            val uploadTask = imageRef.putFile(uri).await()
                            val downloadUrl = uploadTask.storage.downloadUrl.await()
                            uploadedImageInfo.add(Pair(fileName,downloadUrl.toString()))
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
                deferredUpload.awaitAll()
            }
            Resource.Success(uploadedImageInfo)

        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun deleteAcademicAtachements(level: String, names: List<String>): Resource<Any> {
        return try {
            val filePath = "${preference.dbTable}/Academic/IAC/"
            withContext(Dispatchers.IO) {
                val deletionTasks = names.map {
                    storageRef.child(filePath + it).delete()
                }
                Tasks.await(Tasks.whenAll(deletionTasks))
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadPaidReceipt(fileUri: Uri) : Resource<Pair<String,String>> {
        return try {
            val file = File(fileUri.path!!)
            val fileName = file.name + "${GenerateRandomNumber.generateRandomNumber(111..999)}"
            val imageRef = storageRef.child(preference.dbTable + "/PaidReceipt/" + fileName)
            val uploadTask = imageRef.putFile(fileUri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            val uploadImageInfo = Pair(fileName, downloadUrl.toString())
            Resource.Success(uploadImageInfo)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun uploadProfileImage(fileUri: Uri): Resource<Pair<String,String>> {
        return try {
            val file = File(fileUri.path!!)
            val fileName = file.name + "${GenerateRandomNumber.generateRandomNumber(111..999)}"
            val imageRef = storageRef.child(preference.dbTable + "/Profile/" + fileName)
            val uploadTask = imageRef.putFile(fileUri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            val uploadedImageInfo = Pair(fileName,downloadUrl.toString())
            Resource.Success(uploadedImageInfo)
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}