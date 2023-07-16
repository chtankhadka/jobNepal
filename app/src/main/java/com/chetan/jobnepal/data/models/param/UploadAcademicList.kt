package com.chetan.jobnepal.data.models.param

data class UploadAcademicList(
    val see: List<seeColl> = emptyList(),
    val intermediate: List<intermediateColl> = emptyList(),
    val bachelor: List<bachelorColl> = emptyList()
) {
    data class seeColl(
        val id: String,
        val date: String,
        val url: String
    )
    data class intermediateColl(
        val id: String,
        val date: String,
        val url: String
    )
    data class bachelorColl(
        val id: String,
        val date: String,
        val url: String
    )

}
