//package com.chetan.jobnepal.utils.file
//
//import android.content.Context
//import android.net.Uri
//import androidx.core.content.FileProvider
//import java.io.File
//import java.io.FileOutputStream
//
//private const val FILE_PROVIDER = BuildConfig.APPLICATION_ID + ".fileprovider"
//fun Context.getUriForFile(file: File): Uri = FileProvider.getUriForFile(
//    this, FILE_PROVIDER, file
//)
//
//fun Context.createFileInCache(
//    fileName: String
//): File = File(cacheDir, fileName)
//
//fun Context.importFileInCache(uri: Uri): File {
//    val fileInformation = FileInformation.fromUri(this, uri)
//    // Creating Temp file
//    val tempFile = createFileInCache(fileInformation.fileName)
//
//    tempFile.createNewFile()
//    tempFile.deleteOnExit()
//
//    val fileOutputStream = FileOutputStream(tempFile)
//
//    contentResolver.openInputStream(uri)?.use {
//        it.copyTo(fileOutputStream)
//    }
//
//    fileOutputStream.flush()
//    fileOutputStream.close()
//
//    return tempFile
//}
