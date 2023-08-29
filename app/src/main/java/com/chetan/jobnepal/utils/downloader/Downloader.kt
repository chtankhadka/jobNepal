package com.chetan.jobnepal.utils.downloader

interface Downloader {
    fun downloadFile(url: String, fileName: String): Long
}