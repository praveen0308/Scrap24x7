package com.jmm.brsap.scrap24x7.model.network_models

data class AddressType(
    val created_at: String? = null,
    val is_active: Int? = null,
    val name: String? = null,
    val type_id: Int? = null
){
    override fun toString(): String {
        return name.toString()
    }
}