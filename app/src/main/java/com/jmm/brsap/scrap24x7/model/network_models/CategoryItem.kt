package com.jmm.brsap.scrap24x7.model.network_models

data class CategoryItem(
    val category_id: Int?=null,
    val created_at: String?=null,
    val created_by: Any?=null,
    val description: String?=null,
    val id: Int?=null,
    val is_active: Int?=null,
    val item_name: String?=null,
    val type: String?=null,
    val unit_id: Int?=null,
    val unit_name: String?=null,
    val unit_price: Double?=null,
    val updated_at: String?=null,
    val updated_by: Any?=null,

    //custom
    var isSelected:Boolean=false
)