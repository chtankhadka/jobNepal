package com.chetan.jobnepal.data.models.param


data class UploadNewVideoLink(
    val dataColl: List<DataColl> = emptyList(),
    ) {
    data class DataColl(
        val id: String = "",
        val title: String = "",
        val videoLink: String = "",
        val description: String = "",
        val publishedTime: String = "",
        val academicList: AcademicList
    ){
        data class AcademicList(
            val technicalList: List<TechnicalList> = emptyList(),
            val nonTechnicalList: List<NonTechnicalList> = emptyList()
        ){
            data class TechnicalList(
                val jobName : String =""
            )
            data class NonTechnicalList(
                val jobName: String = ""
            )
        }
    }
}

