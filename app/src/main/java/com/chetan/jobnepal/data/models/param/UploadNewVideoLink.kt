package com.chetan.jobnepal.data.models.param


data class UploadNewVideoLink(
    val id: String = "",
    val data: List<DataColl>,

    ) {
    data class DataColl(
        val id: String = "",
        val title: String = "",
        val videoLink: String = "",
        val description: String = ""
    )
}
