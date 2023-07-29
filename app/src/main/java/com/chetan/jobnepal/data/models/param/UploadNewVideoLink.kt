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
        val availableJobList: List<JobList> = emptyList()
    ){
        data class JobList(
            val jobName: String = "",
            val jobId: String = ""
        )
    }
}

