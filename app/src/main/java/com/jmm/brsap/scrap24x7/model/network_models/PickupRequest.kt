package com.jmm.brsap.scrap24x7.model.network_models

data class PickupRequest(
    val id:Int?=null,
    val created_at: String? = null,
    val customer_id: Int? = null,
    val is_active: Any? = null,
    val location_id: Int? = null,
    val pickup_address_id: Int? = null,
    val pickup_id: String? = null,
    val pickup_request_attendedBy: Int? = null,
    val pickup_request_items: List<PickupRequestItem>? = null,
    val pickup_requested_date: Any? = null,
    val raised_on: String? = null,
    val pickup_status: Int? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null,
    val warehouse_id: Any? = null,

    //custom
    val customer_address:CustomerAddress?=null,
    val status_name:String?=null
)