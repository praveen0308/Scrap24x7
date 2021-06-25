package com.jmm.brsap.scrap24x7.model.network_models

data class Warehouse(
    val created_at: String? = null,
    val created_by: Any? = null,
    val id: Int? = null,
    val is_active: Int? = null,
    val location_id: Int? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null,
    val warehouse_details: String? = null,
    val warehouse_name: String? = null,

// custom
    val location_name : String?=null
)