package com.jmm.brsap.scrap24x7.model.network_models

data class ApiResponse(
    val code: Int,
    val data: Data,
    val locale: String,
    val message: String,
    val success: Boolean
)