package com.jmm.brsap.scrap24x7.model

data class ApiRequestModel(
    val from: String? = null,
    val location_ids: List<Int>? = null,
    val to: String? = null
)