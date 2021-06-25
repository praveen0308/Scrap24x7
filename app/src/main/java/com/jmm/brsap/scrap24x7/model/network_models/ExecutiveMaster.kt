package com.jmm.brsap.scrap24x7.model.network_models

data class ExecutiveMaster(
    val aadhaar_number: String? = null,
    val address: String? = null,
    val alternative_mobile_no: String? = null,
    val created_at: String? = null,
    val created_by: Any? = null,
    val id: Int? = null,
    val is_active: Int? = null,
    val location_id: Int? = null,
    val mobile_no: String? = null,
    val name: String? = null,
    val updated_at: Any? = null,
    val updated_by: Any? = null,

// custom
    val location_name : String?=null
)