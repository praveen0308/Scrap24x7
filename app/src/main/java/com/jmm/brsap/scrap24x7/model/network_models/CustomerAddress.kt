package com.jmm.brsap.scrap24x7.model.network_models

import com.google.gson.annotations.SerializedName

data class CustomerAddress(
    val address1: Any? = null,
    val address2: Any? = null,
    val address_type_id: Int? = null,
    val apartment: Any? = null,
    val building: Any? = null,
    val city: String? = null,
    val country: String? = null,
    val created_at: String? = null,
    val created_by: Any? = null,
    val customer_id: Int? = null,
    val google_address: Any? = null,
    val id: Int? = null,
    val isDefault: Any? = null,
    val is_active: Any? = null,
    val latitude: Any? = null,
    val locality: String? = null,
    val longitude: Any? = null,
    val state: String? = null,
    val street: String? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null,
    val location_id: Int? = null,

    @SerializedName("name")
    val address_type : String?=null,

    //custom
    var isSelected:Boolean = false


)