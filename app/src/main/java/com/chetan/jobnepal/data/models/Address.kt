package com.chetan.jobnepal.data.models

data class Address(
    val provinces: List<Province> = emptyList()
) {
    data class Province(
        val districts: List<District> = emptyList(),
        val name: String = ""
    ) {
        data class District(
            val municipalities: List<String> = emptyList(),
            val name: String = "",
            val ruralMunicipalities: List<String>?
        )
    }
}