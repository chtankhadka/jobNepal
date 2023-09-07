package com.chetan.jobnepal.screens.admin.uploadvideo


sealed interface UploadVideoEvent{
    data object UploadVideoUrl : UploadVideoEvent
    class IdChange(var value: String) : UploadVideoEvent
    class UrlIdChange(var value: String) : UploadVideoEvent
    class OnVideoUrlChange(var value: String) : UploadVideoEvent
    class DescriptionChange(var value: String) : UploadVideoEvent
    class TitleChange(var value: String) : UploadVideoEvent
    class OnSelectProvince(var value: String) : UploadVideoEvent
    data object DismissInfoMsg : UploadVideoEvent
}