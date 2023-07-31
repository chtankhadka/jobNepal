package com.chetan.jobnepal.data.models.dashboard

data class FormAppliedList(
    val dataColl: List<DataColl> = emptyList()
){
    data class DataColl(
        val id: String = "",
        val title: String = "",
        val videoLink: String = "",
        val description: String = "",
        val apply: String = "",
        val paymentSuccess: Boolean = false,
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



