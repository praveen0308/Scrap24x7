package com.jmm.brsap.scrap24x7.model.network_models

data class UserTypeMaster(
    val created_at: String? = null,
    val created_by: Int? = null,
    val description: String? = null,
    val id: Int? = null,
    val isVisible: Int? = null,
    val is_active: Int? = null,
    val updated_at: String? = null,
    val updated_by: Int? = null,
    val user_type: String? = null
){
    override fun toString(): String {
        return user_type.toString()
    }
}