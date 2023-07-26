package com.chetan.jobnepal.screens.admin.uploadvideo

sealed interface UploadVideoEvent{
    object UploadVideoUrl : UploadVideoEvent
    object DownloadVideoUrl : UploadVideoEvent
    object Reset : UploadVideoEvent
    class IdChange(var value: String) : UploadVideoEvent
    class UrlChange(var value: String) : UploadVideoEvent
    class DescriptionChange(var value: String) : UploadVideoEvent
    class TitleChange(var value: String) : UploadVideoEvent
}