package com.chetan.jobnepal.data.models.formrequest

data class FormRequestJobDetails(
    val id: String = "",
    val title: String = "",
    val videoLink: String = "",
    val description: String = "",
    val apply: String = "",
    val paymentSuccess: Boolean = false,
    val academicList: List<AcademicList> = emptyList()
)
data class AcademicList(
    val listName: String = "",
    val jobList: List<AvailableJobs> = emptyList(),
    val levels: List<AvailableLevels> = emptyList()
)

data class AvailableJobs(
    val jobName: String = ""
)

data class AvailableLevels(
    val levelName: String = ""
)