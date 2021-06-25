package com.jmm.brsap.scrap24x7.model.network_models

import java.io.Serializable

data class LocationMaster(
    val city_id: Any? = null,
    val country_id: Int? = null,
    val created_at: String? = null,
    val created_by: Any? = null,
    val id: Int? = null,
    val is_active: Int? = null,
    var location_details: String? = null,
    var location_name: String? = null,
    val state_id: Int? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null
):Serializable{
    override fun toString(): String {
        return location_name.toString()
    }
}