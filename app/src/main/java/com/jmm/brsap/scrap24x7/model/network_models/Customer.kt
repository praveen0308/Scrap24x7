package com.jmm.brsap.scrap24x7.model.network_models

import java.io.Serializable

data class Customer(
    val aadhar_number: String?=null,
    val created_at: String?=null,
    val created_by: Any?=null,
    val customer_id: Int?=null,
    val dob: Any?=null,
    val email_id: String,
    val first_name: String,
    val is_active: Any?=null,
    val last_name: String,
    val mobile_number: String,
    val pan_card: Any?=null,
    val updated_at: String?=null,
    val updated_by: Any?=null,
    val user_id: String?=null,
    val user_type_id: Int?=null,

// Custom

    val earning:Double?=null,
    val request_count:Int?=null,
):Serializable