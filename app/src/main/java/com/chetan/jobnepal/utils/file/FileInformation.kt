package com.chetan.jobnepal.utils.file

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap

data class FileInformation(
    val mineType: String?,
    val extension: String?,
    val fileName: String,
    val fileSize: Long
) {
    companion object {
        fun fromUri(context: Context, uri: Uri): FileInformation {
            val mimeType: String? = context.contentResolver.getType(uri)
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

            return context.contentResolver.query(uri, null, null, null, null)!!.use { cursor ->
                cursor.moveToFirst()

                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)

                val fileInformation = FileInformation(
                    mineType = mimeType,
                    extension = extension,
                    fileName = cursor.getString(nameIndex),
                    fileSize = cursor.getLong(sizeIndex)
                )
                cursor.close()
                fileInformation
            }
        }
    }
}
