package com.jmm.brsap.scrap24x7.model.network_models

data class DriverMaster(
    val created_at: String? = null,
    val created_by: Int? = null,
    val driver_aadhar_number: String? = null,
    val driver_address: String? = null,
    val driver_email_id: String? = null,
    val driver_mobile_number1: String? = null,
    val driver_mobile_number2: String? = null,
    val driver_name: String? = null,
    val driver_pan_number: String? = null,
    val id: Int? = null,
    val is_active: Int? = null,
    val location_id: Int? = null,
    val updated_at: Any? = null,
    val updated_by: Any? = null,
    val vehicle_state: String? = null,


// custom
    val location_name : String?=null
)
