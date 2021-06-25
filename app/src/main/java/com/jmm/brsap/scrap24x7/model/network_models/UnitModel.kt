package com.jmm.brsap.scrap24x7.model.network_models

data class UnitModel(
    val created_at: String? = null,
    val created_by: Any? = null,
    val description: String? = null,
    val id: Int? = null,
    val is_active: Int? = null,
    val unit_name: String? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null
){
    override fun toString(): String {
        return unit_name.toString()
    }
}