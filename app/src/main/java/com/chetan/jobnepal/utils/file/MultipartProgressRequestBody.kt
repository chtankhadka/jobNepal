//package com.chetan.jobnepal.utils.file
//
//import android.os.Handler
//import android.os.Looper
//import java.io.File
//import java.io.FileInputStream
//import okhttp3.MediaType
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.RequestBody
//import okio.BufferedSink
//
//class MultipartProgressRequestBody(
//    private val mediaType: String = "*/*",
//    private val file: File,
//    private val progressListener: ProgressListener
//) : RequestBody() {
//
//    override fun contentType(): MediaType? {
//        return mediaType.toMediaTypeOrNull()
//    }
//
//    override fun writeTo(sink: BufferedSink) {
//        val length = file.length()
//        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
//
//        val fileInputStream = FileInputStream(file)
//
//        var uploaded = 0L
//
//        fileInputStream.use { inpStream ->
//            var read: Int
//            val handler = Handler(Looper.getMainLooper())
//
//            while (inpStream.read(buffer).also { read = it } != -1) {
//                handler.post(ProgressUpdater(uploaded, length))
//                uploaded += read
//
//                sink.write(buffer, 0, read)
//            }
//        }
//    }
//
//    override fun contentLength(): Long {
//        return file.length()
//    }
//
//    interface ProgressListener {
//        fun onProgressUpdate(percentage: Float)
//    }
//
//    inner class ProgressUpdater(
//        private val uploaded: Long,
//        private val total: Long
//    ) : Runnable {
//        override fun run() {
//            val percentage = (100 * uploaded / total).toFloat()
//            progressListener.onProgressUpdate(percentage)
//        }
//    }
//
//    companion object {
//        private const val DEFAULT_BUFFER_SIZE = 2048
//    }
//}
