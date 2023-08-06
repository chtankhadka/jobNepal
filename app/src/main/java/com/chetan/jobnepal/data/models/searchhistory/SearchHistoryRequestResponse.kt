package com.chetan.jobnepal.data.models.searchhistory

data class SearchHistoryRequestResponse(
    val dataColl: List<DataColl> = emptyList()

){
    data class DataColl(
        val searchValue: String = "",
        val searchTime: String = ""
    )
}
