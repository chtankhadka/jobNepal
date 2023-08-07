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
        val province: String = "",
        val academicList: List<AcademicList> = emptyList()
    ){
        data class AcademicList(
            val listName: String = "",
            val jobList: List<AvailableJobs> = emptyList(),
            val levels: List<AvailableLevels> = emptyList()
        ){
            data class AvailableJobs(
                val jobName : String =""
            )
            data class AvailableLevels(
                val levelName: String = ""
            )
        }
    }
}

