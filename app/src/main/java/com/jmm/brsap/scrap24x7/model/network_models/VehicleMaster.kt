package com.jmm.brsap.scrap24x7.model.network_models

import java.io.Serializable

data class VehicleMaster(
    val chassis_number: String? = null,
    val created_at: String? = null,
    val created_by: Any? = null,
    val id: Int? = null,
    val is_active: Int? = null,
    val location_id: Int? = null,
    val owner_address: String? = null,
    val owner_email_id: String? = null,
    val owner_mobile_number: String? = null,
    val owner_name: String? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null,
    val vehicle_color: String? = null,
    val vehicle_make: String? = null,
    val vehicle_model: String? = null,
    val vehicle_number: String? = null,
    val vehicle_plate_no: String? = null,
    val vehicle_state: String? = null,
    val vehicle_year: String? = null,
    val location_name:String? = null

)