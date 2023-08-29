package com.chetan.jobnepal.utils.downloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri


class AndroidDownloader(
    private val context: Context,
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String,fileName: String): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context,null,fileName)
        return downloadManager.enqueue(request)

    }
}