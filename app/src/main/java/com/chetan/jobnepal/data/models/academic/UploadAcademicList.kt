package com.chetan.jobnepal.data.models.academic

data class UploadAcademicList(
    val SEE: List<seeColl> = emptyList(),
    val IAC: List<iacColl> = emptyList(),
    val BSc_CSIT: List<bsccsitColl> = emptyList(),
    val CITIZENSHIP: List<citizenship> = emptyList(),
    val experience : List<Experience> = emptyList(),
    val training : List<Training> = emptyList()
) {
    data class seeColl(
        val id: String,
        val date: String,
        val name: String,
        val url: String
    ){
        constructor() : this("", "", "","")
    }
    data class iacColl(
        val id: String,
        val date: String,
        val name: String,
        val url: String
    ){
        constructor() : this("", "", "","")
    }
    data class bsccsitColl(
        val id: String,
        val date: String,
        val name: String,
        val url: String
    ){
        constructor() : this("", "", "","")
    }
    data class citizenship(
        val id: String,
        val date: String,
        val name: String,
        val url: String
    ){
        constructor() : this("", "", "","")
    }
    data class Experience(
        val id: String,
        val date: String,
        val name: String,
        val url: String
    ){
        constructor() : this("", "", "","")
    }
    data class Training(
        val id: String,
        val date: String,
        val name: String,
        val url: String
    ){
        constructor() : this("", "", "","")
    }

}
